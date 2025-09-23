package com.aliayali.market_baz.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.market_baz.data.local.database.MyDataBase
import com.aliayali.market_baz.data.model.OrderStatus

@Entity(tableName = MyDataBase.ORDER_TABLE)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userPhone: String,
    val productId: Int,
    val quantity: Int = 1,
    val totalPrice: Double,
    val status: String = OrderStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis(),
)