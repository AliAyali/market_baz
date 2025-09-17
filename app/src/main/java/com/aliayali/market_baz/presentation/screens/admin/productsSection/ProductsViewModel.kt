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
    private val _product = mutableStateOf<List<ProductEntity>>(emptyList())
    val product: State<List<ProductEntity>> = _product

    init {
        viewModelScope.launch {
            productRepository.getAllProducts().collect { products ->
                _product.value = products
            }
        }
    }

}