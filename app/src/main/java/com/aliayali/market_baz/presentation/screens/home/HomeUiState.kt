package com.aliayali.market_baz.presentation.screens.home

import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity

data class HomeUiState(
    val user: UserEntity? = null,
    val products: List<ProductEntity> = emptyList(),
    val filteredProducts: List<ProductEntity> = emptyList(),
    val searchResults: List<ProductEntity> = emptyList(),
    val shoppingCartSize: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)