package com.aliayali.market_baz.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.market_baz.data.local.database.MyDataBase

@Entity(tableName = MyDataBase.USER_TABLE)
data class UserEntity(
    @PrimaryKey val phone: String,
    val name: String?,
    val isLoggedIn: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
)
