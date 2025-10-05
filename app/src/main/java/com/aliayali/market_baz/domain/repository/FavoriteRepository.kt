package com.aliayali.market_baz.domain.repository

import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavoriteByProductId(id: String, userPhone: String)
    suspend fun deleteFavorite(favorite: FavoriteEntity)
    fun getAllFavorites(userPhone: String): Flow<List<FavoriteEntity>>
    suspend fun isFavorite(id: String, userPhone: String): Boolean
}
