package com.aliayali.market_baz.presentation.screens.login

import com.aliayali.market_baz.data.local.database.entity.UserEntity

data class LoginUiState(
    val isLoading: Boolean = false,
    val user: UserEntity? = null,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false,
)