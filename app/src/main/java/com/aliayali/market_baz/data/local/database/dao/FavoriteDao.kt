package com.aliayali.market_baz.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites_table WHERE productId = :id AND userPhone = :userPhone")
    suspend fun deleteFavoriteByProductId(id: Int, userPhone: String)

    @Query("SELECT * FROM favorites_table WHERE userPhone = :userPhone")
    fun getAllFavorites(userPhone: String): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites_table WHERE productId = :id AND userPhone = :userPhone)")
    suspend fun isFavorite(id: Int, userPhone: String): Boolean
}
