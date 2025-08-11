package com.aliayali.market_baz.data.local.database.dao

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.aliayali.market_baz.data.local.database.entity.ProductEntity

@Entity(
    tableName = "comments",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["productId"])]
)
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val commentId: Int = 0,
    val productId: Int,
    val username: String,
    val text: String,
    val createdAt: Long = System.currentTimeMillis(),
)