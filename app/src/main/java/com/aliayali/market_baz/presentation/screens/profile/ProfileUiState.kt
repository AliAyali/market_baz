package com.aliayali.market_baz.presentation.screens.profile

import com.aliayali.market_baz.data.local.database.entity.UserEntity

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: UserEntity? = null,
    val phone: String = "",
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false,
)