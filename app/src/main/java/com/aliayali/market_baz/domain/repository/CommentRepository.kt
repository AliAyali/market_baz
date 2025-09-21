package com.aliayali.market_baz.domain.repository

import androidx.room.Query
import com.aliayali.market_baz.data.local.database.entity.CommentEntity
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun insertComment(comment: CommentEntity)
    suspend fun deleteComment(comment: CommentEntity)
    fun getCommentsByProductId(productId: Int): Flow<List<CommentEntity>>
    suspend fun getCommentsCount(): Int
}