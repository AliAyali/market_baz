package com.aliayali.market_baz.presentation.screens.admin.ordersSection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.OrderEntity
import com.aliayali.market_baz.domain.repository.OrderRepository
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {

    data class OrderUiState(
        val order: OrderEntity,
        val userAddress: String = "",
        val productNames: String = "",
    )

    private val _ordersState = MutableStateFlow<List<OrderUiState>>(emptyList())
    val ordersState: StateFlow<List<OrderUiState>> = _ordersState

    init {
        viewModelScope.launch {
            orderRepository.getAllOrders().collect { orders ->
                val uiList = orders.map { order ->
                    val user = userRepository.getUserByPhone(order.userPhone)

                    val product = productRepository.getProductById(order.productId)
                    val productName = product?.name ?: ""

                    OrderUiState(
                        order = order,
                        userAddress = user?.address ?: "",
                        productNames = productName
                    )
                }
                _ordersState.value = uiList
            }
        }
    }

    fun updateOrderStatus(order: OrderEntity, newStatus: String) {
        viewModelScope.launch {
            val updatedOrder = order.copy(status = newStatus)
            orderRepository.updateOrder(updatedOrder)
        }
    }

    fun deleteAllUserOrders(userPhone: String) {
        viewModelScope.launch {
            ordersState.value
                .filter { it.order.userPhone == userPhone }
                .forEach { orderEntity ->
                    orderRepository.deleteOrder(orderEntity.order)
                }
        }
    }

}
