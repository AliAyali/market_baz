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

    private val _isLoadingAdd = MutableStateFlow(false)
    val isLoadingAdd: StateFlow<Boolean> = _isLoadingAdd
    private val _isLoadingDelete = MutableStateFlow(false)
    val isLoadingDelete: StateFlow<Boolean> = _isLoadingDelete
    private val _isLoadingScreen = MutableStateFlow(false)
    val isLoadingScreen: StateFlow<Boolean> = _isLoadingScreen

    fun addProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                _isLoadingAdd.value = true
                productRepository.insertProducts(product)
                _isLoadingAdd.value = false
                onSuccess()
            } catch (e: Exception) {
                _isLoadingAdd.value = false
                onError(e.message ?: "خطا در افزودن محصول")
            }
        }
    }

    fun updateProduct(product: Product, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoadingAdd.value = true
                if (product.id.isNullOrEmpty()) throw Exception("Product ID نامعتبر است")
                productRepository.updateProduct(product)
                _isLoadingAdd.value = false
                onSuccess()
            } catch (e: Exception) {
                _isLoadingAdd.value = false
                onError(e.message ?: "خطا در بروزرسانی محصول")
            }
        }
    }

    fun deleteProduct(product: Product, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoadingDelete.value = true
                if (product.id.isNullOrEmpty()) throw Exception("Product ID نامعتبر است")
                productRepository.deleteProduct(product)
                _isLoadingDelete.value = false
                onSuccess()
            } catch (e: Exception) {
                _isLoadingDelete.value = false
                onError(e.message ?: "خطا در حذف محصول")
            }
        }
    }

    fun getProductById(id: String, onResult: (Product?) -> Unit) {
        viewModelScope.launch {
            try {
                if (id.isEmpty()) throw Exception("Product ID نامعتبر است")
                _isLoadingScreen.value = true
                val product = productRepository.getProductById(id)
                _isLoadingScreen.value = false
                onResult(product)
            } catch (e: Exception) {
                _isLoadingScreen.value = false
                onResult(null)
            }
        }
    }

}
