package com.aliayali.market_baz.domain.repository

import com.aliayali.market_baz.data.local.database.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun insertOrder(order: OrderEntity)
    suspend fun updateOrder(order: OrderEntity)
    fun getAllOrders(): Flow<List<OrderEntity>>
    fun getOrdersByUser(phone: String): Flow<List<OrderEntity>>
    fun getOrdersByStatus(status: String): Flow<List<OrderEntity>>
    fun getOrdersCount(): Flow<Int>
    fun getOrdersCountByStatus(status: String): Flow<Int>
    suspend fun deleteOrder(order: OrderEntity)
}