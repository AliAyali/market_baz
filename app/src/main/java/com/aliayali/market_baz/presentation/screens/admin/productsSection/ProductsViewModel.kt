package com.aliayali.market_baz.presentation.screens.admin.productsSection

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    productRepository: ProductRepository
) : ViewModel() {

    private val _allProducts = mutableStateOf<List<ProductEntity>>(emptyList())
    val allProducts: State<List<ProductEntity>> = _allProducts

    private val _displayedProducts = mutableStateOf<List<ProductEntity>>(emptyList())
    val displayedProducts: State<List<ProductEntity>> = _displayedProducts

    init {
        viewModelScope.launch {
            productRepository.getAllProducts().collect { products ->
                _allProducts.value = products
                _displayedProducts.value = products
            }
        }
    }

    fun searchProducts(query: String) {
        _displayedProducts.value = if (query.isBlank()) {
            _allProducts.value
        } else {
            _allProducts.value.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}