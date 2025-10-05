package com.aliayali.market_baz.data.local.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.aliayali.market_baz.data.local.database.MyDataBase

@Entity(
    tableName = MyDataBase.RATING_TABLE,
    indices = [Index(value = ["userPhone", "productId"], unique = true)]
)
data class RatingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val userPhone: String,
    val rating: Int,
)