package com.aliayali.market_baz.presentation.screens.home

import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.domain.model.Product

data class HomeUiState(
    val user: UserEntity? = null,
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val searchResults: List<Product> = emptyList(),
    val shoppingCartSize: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)