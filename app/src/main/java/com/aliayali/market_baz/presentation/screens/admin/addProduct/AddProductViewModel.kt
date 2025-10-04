package com.aliayali.market_baz.presentation.screens.admin.addProduct

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
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun addProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                productRepository.insertProducts(product)
                _isLoading.value = false
                onSuccess()
            } catch (e: Exception) {
                _isLoading.value = false
                onError(e.message ?: "خطا در افزودن محصول")
            }
        }
    }

    fun updateProduct(product: Product, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                if (product.id.isNullOrEmpty()) throw Exception("Product ID نامعتبر است")
                productRepository.updateProduct(product)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "خطا در بروزرسانی محصول")
            }
        }
    }

    fun deleteProduct(product: Product, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                if (product.id.isNullOrEmpty()) throw Exception("Product ID نامعتبر است")
                productRepository.deleteProduct(product)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "خطا در حذف محصول")
            }
        }
    }

    fun getProductById(id: String, onResult: (Product?) -> Unit) {
        viewModelScope.launch {
            try {
                if (id.isEmpty()) throw Exception("Product ID نامعتبر است")
                val product = productRepository.getProductById(id)
                onResult(product)
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

}
