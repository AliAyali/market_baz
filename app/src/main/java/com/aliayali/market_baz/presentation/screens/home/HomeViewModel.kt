package com.aliayali.market_baz.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val userPreferences: UserPreferences,
    private val shoppingCardRepository: ShoppingCardRepository,
) : ViewModel() {

    private val _shoppingCardRepositorySize = mutableIntStateOf(0)
    val shoppingCardRepositorySize: State<Int> = _shoppingCardRepositorySize

    private val _user = mutableStateOf<UserEntity?>(null)
    val user: State<UserEntity?> = _user

    private val _product = mutableStateOf<List<ProductEntity>>(emptyList())
    val product: State<List<ProductEntity>> = _product

    private var _phone = mutableStateOf("")
    private var _category = mutableIntStateOf(0)
    private val _filteredProducts = mutableStateOf<List<ProductEntity>>(listOf())
    val filteredProducts: State<List<ProductEntity>> = _filteredProducts
    private val _searchResults = mutableStateOf<List<ProductEntity>>(emptyList())
    val searchResults: State<List<ProductEntity>> = _searchResults


    fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            val result = userRepository.getUserByPhone(phone)
            if (result != null) {
                _user.value = result
            } else {
                _user.value = null
            }
        }
    }

    init {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let {
                    _phone.value = it
                    getUserByPhone(it)
                    collectShoppingCart(it)
                }
            }
        }

        viewModelScope.launch {
            productRepository.getAllProducts().collect { products ->
                _product.value = products
            }
        }
    }

    private fun collectShoppingCart(phone: String) {
        viewModelScope.launch {
            shoppingCardRepository.getAllItems(phone).collect { items ->
                _shoppingCardRepositorySize.intValue = items.size
            }
        }
    }

    fun getCategory(categoryId: Int) {
        _category.intValue = categoryId
        viewModelScope.launch {
            productRepository.getProductsByCategorySortedByStar(categoryId).collect { products ->
                _filteredProducts.value = products
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _searchResults.value = emptyList()
            } else {
                _searchResults.value = product.value.filter { product ->
                    product.name.contains(query, ignoreCase = true)
                }
            }
        }
    }

}