package com.aliayali.market_baz.presentation.screens.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.core.utils.NetworkUtils.hasInternetConnection
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    private val _hasInternet = MutableStateFlow(false)
    val hasInternet: StateFlow<Boolean> = _hasInternet.asStateFlow()

    private var delayJobStarted = false

    init {
        viewModelScope.launch {
            userPreferences.isLoggedIn.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                false
            ).collect { loggedIn ->
                updateState { it.copy(isLoggedIn = loggedIn) }
            }
        }

        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phone ->
                phone?.let { getUserByPhone(it) }
            }
        }
    }

    fun checkInternet(context: Context) {
        viewModelScope.launch {
            val connected = hasInternetConnection(context)
            _hasInternet.value = connected
        }
    }

    fun startDelayOnce(millis: Long = 3000L) {
        if (delayJobStarted) return
        delayJobStarted = true
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, isDelayFinished = false) }
            delay(millis)
            updateState { it.copy(isLoading = false, isDelayFinished = true) }
        }
    }

    fun retry(context: Context) {
        checkInternet(context)
        delayJobStarted = false
        startDelayOnce(1000L)
    }

    private fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            runCatching { repository.getUserByPhone(phone) }
                .onSuccess { user ->
                    updateState { it.copy(isLoading = false, user = user) }
                }
                .onFailure { e ->
                    updateState { it.copy(isLoading = false, errorMessage = e.message) }
                }
        }
    }

    private fun updateState(transform: (SplashUiState) -> SplashUiState) {
        _uiState.update(transform)
    }

    fun clearError() {
        updateState { it.copy(errorMessage = null) }
    }
}