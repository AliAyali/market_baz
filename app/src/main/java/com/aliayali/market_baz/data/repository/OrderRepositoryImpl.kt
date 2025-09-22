package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.OrderDao
import com.aliayali.market_baz.data.local.database.entity.OrderEntity
import com.aliayali.market_baz.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val dao: OrderDao,
) : OrderRepository {
    override suspend fun insertOrder(order: OrderEntity) {
        dao.insertOrder(order)
    }

    override suspend fun updateOrder(order: OrderEntity) {
        dao.updateOrder(order)
    }

    override fun getAllOrders(): Flow<List<OrderEntity>> = dao.getAllOrders()
    override fun getOrdersByUser(phone: String): Flow<List<OrderEntity>> =
        dao.getOrdersByUser(phone)

    override fun getOrdersByStatus(status: String): Flow<List<OrderEntity>> =
        dao.getOrdersByStatus(status)

    override fun getOrdersCount(): Flow<Int> = dao.getOrdersCount()
    override fun getOrdersCountByStatus(status: String): Flow<Int> =
        dao.getOrdersCountByStatus(status)
}