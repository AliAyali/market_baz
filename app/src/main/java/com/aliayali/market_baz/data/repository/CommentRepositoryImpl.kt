package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.CommentDao
import com.aliayali.market_baz.data.local.database.entity.CommentEntity
import com.aliayali.market_baz.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val dao: CommentDao,
) : CommentRepository {
    override suspend fun insertComment(comment: CommentEntity) {
        dao.insertComment(comment)
    }

    override suspend fun deleteComment(comment: CommentEntity) {
        dao.deleteComment(comment)
    }

    override fun getCommentsByProductId(productId: Int) =
        dao.getCommentsByProductId(productId)
}