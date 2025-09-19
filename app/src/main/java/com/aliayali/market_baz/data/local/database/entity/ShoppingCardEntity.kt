package com.aliayali.market_baz.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.market_baz.data.local.database.MyDataBase.Companion.SHOPPING_CARD_TABLE

@Entity(tableName = SHOPPING_CARD_TABLE)
data class ShoppingCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userPhone: String,
    val productId: Int,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val number: Int = 0,
)