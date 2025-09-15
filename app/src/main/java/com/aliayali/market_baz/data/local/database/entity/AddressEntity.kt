package com.aliayali.market_baz.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.market_baz.data.local.database.MyDataBase

@Entity(tableName = MyDataBase.ADDRESS_TABLE)
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userPhone: String,
    val name: String,
    val city: String = "مشهد",
    val street: String,
    val milan: String,
    val plate: String,
    val floor: String,
)