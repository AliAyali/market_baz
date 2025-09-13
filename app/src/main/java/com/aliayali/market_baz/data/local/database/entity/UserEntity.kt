package com.aliayali.market_baz.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.market_baz.R
import com.aliayali.market_baz.data.local.database.MyDataBase

@Entity(tableName = MyDataBase.USER_TABLE)
data class UserEntity(
    @PrimaryKey val phone: String,
    val image: Int = R.drawable.user,
    val name: String?,
    val password: String,
    val address: String = "آدرس تنظیم نشده",
    val isAdmin: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
)
