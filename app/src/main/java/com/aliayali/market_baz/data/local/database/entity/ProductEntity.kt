package com.aliayali.market_baz.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.market_baz.data.local.database.MyDataBase.Companion.PRODUCT_TABLE

@Entity(tableName = PRODUCT_TABLE)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUrl: Int,
    val name: String,
    val description: String?,
    val price: Int,
    val discount: Int,
    val star: Double = 0.0,
    val categoryId: Int,
    val inventory: Int = 0,
    val numberOfComments: Int = 0,
)