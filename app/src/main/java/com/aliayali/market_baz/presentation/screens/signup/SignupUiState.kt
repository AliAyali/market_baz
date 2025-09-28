package com.aliayali.market_baz.presentation.screens.signup

import com.aliayali.market_baz.data.local.database.entity.UserEntity

data class SignupUiState(
    val isLoading: Boolean = false,
    val user: UserEntity? = null,
    val errorMessage: String? = null,
    val isUserCreated: Boolean = false
)