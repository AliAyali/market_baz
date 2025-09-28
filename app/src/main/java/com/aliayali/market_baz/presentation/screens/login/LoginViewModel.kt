package com.aliayali.market_baz.presentation.screens.login

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
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private fun updateState(transform: (LoginUiState) -> LoginUiState) {
        _uiState.update(transform)
    }

    fun setError(message: String) {
        updateState { it.copy(errorMessage = message) }
    }

    fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                repository.getUserByPhone(phone)
            }.onSuccess { user ->
                if (user != null) {
                    updateState { it.copy(isLoading = false, user = user, errorMessage = null) }
                } else {
                    updateState { it.copy(isLoading = false, user = null, errorMessage = "شماره تلفن یافت نشد") }
                }
            }.onFailure { e ->
                updateState { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun login(user: UserEntity) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                userPreferences.savePhoneNumber(user.phone)
                userPreferences.setLoggedIn(true)
            }.onSuccess {
                updateState { it.copy(isLoading = false, isLoggedIn = true) }
            }.onFailure { e ->
                updateState { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun clearError() {
        updateState { it.copy(errorMessage = null) }
    }

    fun clearLoginSuccess() {
        updateState { it.copy(isLoggedIn = false) }
    }
}