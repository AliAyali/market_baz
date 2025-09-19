package com.aliayali.market_baz.presentation.screens.admin.addProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    fun addProduct(product: ProductEntity, onSuccess: () -> Unit) {
        viewModelScope.launch {
            productRepository.insertProducts(product)
            onSuccess()
        }
    }

    fun updateProduct(product: ProductEntity, onSuccess: () -> Unit) {
        viewModelScope.launch {
            productRepository.updateProduct(product)
            onSuccess()
        }
    }

    fun deleteProduct(product: ProductEntity, onSuccess: () -> Unit) {
        viewModelScope.launch {
            productRepository.deleteProduct(product)
            onSuccess()
        }
    }

    fun getProductById(id: Int, onResult: (ProductEntity?) -> Unit) {
        viewModelScope.launch {
            val product = productRepository.getProductById(id)
            onResult(product)
        }
    }
}
