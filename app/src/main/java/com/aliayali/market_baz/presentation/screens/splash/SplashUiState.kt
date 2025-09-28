package com.aliayali.market_baz.presentation.screens.splash

import com.aliayali.market_baz.data.local.database.entity.UserEntity

data class SplashUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isDelayFinished: Boolean = false,
    val user: UserEntity? = null,
    val errorMessage: String? = null,
)