package com.aliayali.market_baz.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let { phone ->
                    updateState { state -> state.copy(phone = phone) }
                    getUserByPhone(phone)
                }
            }
        }

        viewModelScope.launch {
            userPreferences.isLoggedIn.collect { loggedIn ->
                updateState { it.copy(isLoggedIn = loggedIn) }
            }
        }
    }

    private fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            runCatching { userRepository.getUserByPhone(phone) }
                .onSuccess { user ->
                    updateState { it.copy(isLoading = false, user = user) }
                }
                .onFailure { e ->
                    updateState { it.copy(isLoading = false, errorMessage = e.message) }
                }
        }
    }

    fun loginState(state: Boolean) {
        viewModelScope.launch {
            userPreferences.setLoggedIn(state)
        }
    }

    private fun updateState(transform: (ProfileUiState) -> ProfileUiState) {
        _uiState.update(transform)
    }

    fun clearError() {
        updateState { it.copy(errorMessage = null) }
    }
}