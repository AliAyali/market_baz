package com.aliayali.market_baz.presentation.screens.splash

data class SplashUiState(
    val isLoading: Boolean = false,
    val isDelayFinished: Boolean = false,
    val isLoggedIn: Boolean = false,
    val user: Any? = null,
    val errorMessage: String? = null
)