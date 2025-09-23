package com.aliayali.market_baz.presentation.screens.shoppingCart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.OrderEntity
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.data.model.OrderStatus
import com.aliayali.market_baz.domain.repository.OrderRepository
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val shoppingCardRepository: ShoppingCardRepository,
    private val orderRepository: OrderRepository,
) : ViewModel() {

    private val _user = mutableStateOf<UserEntity?>(null)
    val user: State<UserEntity?> = _user

    private val _product = mutableStateOf<List<ProductEntity>>(emptyList())
    val product: State<List<ProductEntity>> = _product
    private var _phone = mutableStateOf("")

    private val _shoppingCardList = mutableStateOf<List<ShoppingCardEntity>>(emptyList())
    val shoppingCardList: State<List<ShoppingCardEntity>> = _shoppingCardList

    private val _hasPreviousOrders = mutableStateOf(false)
    val hasPreviousOrders: State<Boolean> = _hasPreviousOrders


    init {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let {
                    _phone.value = it
                    getUserByPhone(it)
                    collectShoppingCart(it)
                    checkPreviousOrders(it)
                }
            }
        }
    }

    fun checkPreviousOrders(phone: String) {
        viewModelScope.launch {
            orderRepository.getOrdersByUser(phone).collect { orders ->
                _hasPreviousOrders.value = orders.isNotEmpty()
            }
        }
    }


    private fun collectShoppingCart(phone: String) {
        viewModelScope.launch {
            shoppingCardRepository.getAllItems(phone).collect { items ->
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
        viewModelScope.launch(Dispatchers.IO) {
            val productFromDb = productRepository.getProductById(item.productId)
            if (productFromDb != null && item.number < productFromDb.inventory) {
                shoppingCardRepository.updateItem(item.copy(number = item.number + 1))
            }
        }
    }

    fun decreaseItem(item: ShoppingCardEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (item.number > 1) {
                shoppingCardRepository.updateItem(item.copy(number = item.number - 1))
            } else {
                shoppingCardRepository.deleteItem(item)
            }
        }
    }

    fun deleteItem(item: ShoppingCardEntity) {
        viewModelScope.launch {
            shoppingCardRepository.deleteItem(item)
        }
    }

    fun insertOrder(order: OrderEntity) {
        viewModelScope.launch {
            orderRepository.insertOrder(order)
        }
    }

    fun placeOrders(userPhone: String, shoppingCartList: List<ShoppingCardEntity>) {
        viewModelScope.launch {
            shoppingCartList.forEach { item ->
                insertOrder(
                    OrderEntity(
                        id = 0,
                        userPhone = userPhone,
                        productId = item.productId,
                        quantity = item.number,
                        totalPrice = item.price * item.number.toDouble(),
                        status = OrderStatus.PAID
                    )
                )
                deleteItem(item)
            }
        }
    }


}