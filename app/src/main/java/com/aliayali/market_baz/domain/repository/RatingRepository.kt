package com.aliayali.market_baz.domain.repository

import com.aliayali.market_baz.data.local.database.entity.RatingEntity

interface RatingRepository {
    suspend fun insertRating(rating: RatingEntity)
    suspend fun getRatingByUserAndProduct(userPhone: String, productId: String): RatingEntity?
    suspend fun deleteRatingsByProduct(productId: String)
}