package com.aliayali.market_baz.presentation.screen.shoppingCart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val shoppingCardRepository: ShoppingCardRepository,
) : ViewModel() {

    private val _user = mutableStateOf<UserEntity?>(null)
    val user: State<UserEntity?> = _user

    private val _product = mutableStateOf<List<ProductEntity>>(emptyList())
    val product: State<List<ProductEntity>> = _product
    private var _phone = mutableStateOf("")

    private val _shoppingCardList = mutableStateOf<List<ShoppingCardEntity>>(emptyList())
    val shoppingCardList: State<List<ShoppingCardEntity>> = _shoppingCardList


    init {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let {
                    _phone.value = it
                    getUserByPhone(it)
                }
            }
        }
        viewModelScope.launch {
            shoppingCardRepository.getAllItems().collect { items ->
                _shoppingCardList.value = items
            }
        }
    }

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

    fun increaseItem(item: ShoppingCardEntity) {
        viewModelScope.launch {
            val productFromDb = productRepository.getProductById(item.productId)
            if (productFromDb != null) {
                if (item.number < productFromDb.inventory) {
                    shoppingCardRepository.updateItem(item.copy(number = item.number + 1))
                }
            }
        }
    }


    fun decreaseItem(item: ShoppingCardEntity) {
        viewModelScope.launch {
            if (item.number > 1) {
                shoppingCardRepository.updateItem(item.copy(number = item.number - 1))
            }
        }
    }


    fun deleteItem(item: ShoppingCardEntity) {
        viewModelScope.launch {
            shoppingCardRepository.deleteItem(item)
        }
    }

    suspend fun getProductById(productId: Int): ProductEntity? {
        return productRepository.getProductById(productId)
    }

}