package com.aliayali.market_baz.domain.repository

import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavoriteByProductId(id: Int, userPhone: String)
    suspend fun deleteFavorite(favorite: FavoriteEntity)
    fun getAllFavorites(userPhone: String): Flow<List<FavoriteEntity>>
    suspend fun isFavorite(id: Int, userPhone: String): Boolean
}
