package com.aliayali.market_baz.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    private fun updateState(transform: (SignupUiState) -> SignupUiState) {
        _uiState.update(transform)
    }

    fun signup(phone: String, name: String, password: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            runCatching {
                repository.getUserByPhone(phone)
            }.onSuccess { user ->
                if (user == null) {
                    insertUserInternal(phone, name, password)
                } else {
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = "این شماره از قبل وجود دارد"
                        )
                    }
                }
            }.onFailure { e ->
                updateState { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    private fun insertUserInternal(phone: String, name: String, password: String) {
        viewModelScope.launch {
            runCatching {
                repository.insertUser(UserEntity(phone, name = name, password = password))
            }.onSuccess {
                updateState { it.copy(isLoading = false, isUserCreated = true) }
                savePhone(phone)
            }.onFailure { e ->
                updateState { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun getDataByPhone(phone: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            runCatching {
                repository.getUserByPhone(phone)
            }.onSuccess { user ->
                updateState { it.copy(isLoading = false, user = user) }
            }.onFailure { e ->
                updateState { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    private fun savePhone(phone: String) {
        viewModelScope.launch {
            userPreferences.savePhoneNumber(phone)
        }
    }

    fun clearError() {
        updateState { it.copy(errorMessage = null) }
    }

    fun clearSuccess() {
        updateState { it.copy(isUserCreated = false) }
    }
}