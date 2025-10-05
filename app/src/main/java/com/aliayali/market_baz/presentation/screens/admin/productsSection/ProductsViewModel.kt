package com.aliayali.market_baz.presentation.screens.admin.productsSection

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.domain.model.Product
import com.aliayali.market_baz.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    productRepository: ProductRepository,
) : ViewModel() {

    private val _allProducts = mutableStateOf<List<Product>>(emptyList())
    val allProducts: State<List<Product>> = _allProducts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _displayedProducts = mutableStateOf<List<Product>>(emptyList())
    val displayedProducts: State<List<Product>> = _displayedProducts

    init {
        viewModelScope.launch {
            _isLoading.value = true
            productRepository.getAllProducts().collect { products ->
                _allProducts.value = products
                _displayedProducts.value = products
            }
            _isLoading.value = false
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