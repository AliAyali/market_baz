package com.aliayali.market_baz.presentation.screen.product

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
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _product = mutableStateOf<ProductEntity?>(null)
    val product: State<ProductEntity?> = _product

    fun getProductById(id: Int?) {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(id ?: 0)
        }
    }

}