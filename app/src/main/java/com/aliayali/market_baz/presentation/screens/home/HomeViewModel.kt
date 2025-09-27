package com.aliayali.market_baz.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val userPreferences: UserPreferences,
    private val shoppingCartRepository: ShoppingCardRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentPhone: String? = null
    private var currentCategoryId: Int = 0

    init {
        observeUserPreferences()
        observeAllProducts()
    }

    private fun observeUserPreferences() {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let {
                    currentPhone = it
                    getUserByPhone(it)
                    observeShoppingCart(it)
                }
            }
        }
    }

    private fun observeAllProducts() {
        viewModelScope.launch {
            productRepository.getAllProducts()
                .catch { e -> updateState { it.copy(errorMessage = e.message) } }
                .collect { products ->
                    updateState { it.copy(products = products) }
                }
        }
    }

    private fun observeShoppingCart(phone: String) {
        viewModelScope.launch {
            shoppingCartRepository.getAllItems(phone)
                .catch { e -> updateState { it.copy(errorMessage = e.message) } }
                .collect { items ->
                    updateState { it.copy(shoppingCartSize = items.size) }
                }
        }
    }

    fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            runCatching { userRepository.getUserByPhone(phone) }
                .onSuccess { user ->
                    updateState { it.copy(user = user) }
                }
                .onFailure { e ->
                    updateState { it.copy(errorMessage = e.message) }
                }
        }
    }

    fun getCategory(categoryId: Int) {
        currentCategoryId = categoryId
        viewModelScope.launch {
            productRepository.getProductsByCategorySortedByStar(categoryId)
                .catch { e -> updateState { it.copy(errorMessage = e.message) } }
                .collect { products ->
                    updateState { it.copy(filteredProducts = products) }
                }
        }
    }

    fun searchProducts(query: String) {
        val products = uiState.value.products
        val results = if (query.isBlank()) {
            emptyList()
        } else {
            products.filter { it.name.contains(query, ignoreCase = true) }
        }
        updateState { it.copy(searchResults = results) }
    }

    private fun updateState(transform: (HomeUiState) -> HomeUiState) {
        _uiState.update(transform)
    }
}