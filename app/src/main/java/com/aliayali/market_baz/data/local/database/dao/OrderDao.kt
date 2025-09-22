package com.aliayali.market_baz.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aliayali.market_baz.data.local.database.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Query("SELECT * FROM order_table ORDER BY createdAt DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM order_table WHERE userPhone = :phone ORDER BY createdAt DESC")
    fun getOrdersByUser(phone: String): Flow<List<OrderEntity>>

    @Query("SELECT * FROM order_table WHERE status = :status ORDER BY createdAt DESC")
    fun getOrdersByStatus(status: String): Flow<List<OrderEntity>>

    @Query("SELECT COUNT(*) FROM order_table")
    fun getOrdersCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM order_table WHERE status = :status")
    fun getOrdersCountByStatus(status: String): Flow<Int>
}