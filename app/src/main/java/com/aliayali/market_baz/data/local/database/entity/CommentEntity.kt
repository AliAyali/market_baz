package com.aliayali.market_baz.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliayali.market_baz.data.local.database.MyDataBase

@Entity(tableName = MyDataBase.COMMENT_TABLE)
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val productId: String,
    val username: String,
    val userPhone: String,
    val detail: String,
)
