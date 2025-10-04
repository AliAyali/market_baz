package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.RatingDao
import com.aliayali.market_baz.data.local.database.entity.RatingEntity
import com.aliayali.market_baz.domain.repository.RatingRepository
import javax.inject.Inject

class RatingRepositoryImpl @Inject constructor(
    private val dao: RatingDao,
) : RatingRepository {
    override suspend fun insertRating(rating: RatingEntity) {
        dao.insertRating(rating)
    }

    override suspend fun getRatingByUserAndProduct(
        userPhone: String,
        productId: String,
    ): RatingEntity? =
        dao.getRatingByUserAndProduct(userPhone, productId)


    override suspend fun deleteRatingsByProduct(productId: String) {
        dao.deleteRatingsByProduct(productId)
    }
}