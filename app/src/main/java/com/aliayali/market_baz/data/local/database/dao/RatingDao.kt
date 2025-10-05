package com.aliayali.market_baz.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliayali.market_baz.data.local.database.entity.RatingEntity

@Dao
interface RatingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRating(rating: RatingEntity)

    @Query("SELECT * FROM rating_table WHERE userPhone = :userPhone AND productId = :productId LIMIT 1")
    suspend fun getRatingByUserAndProduct(userPhone: String, productId: String): RatingEntity?

    @Query("DELETE FROM rating_table WHERE productId = :productId")
    suspend fun deleteRatingsByProduct(productId: String)

}