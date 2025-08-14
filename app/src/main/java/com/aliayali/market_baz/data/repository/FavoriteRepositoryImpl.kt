package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.FavoriteDao
import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import com.aliayali.market_baz.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao,
) : FavoriteRepository {

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        dao.insertFavorite(favorite)
    }

    override suspend fun deleteFavoriteByProductId(id: Int) {
        dao.deleteFavoriteByProductId(id)
    }

    override suspend fun deleteFavorite(favorite: FavoriteEntity) {
        dao.deleteFavorite(favorite)
    }

    override fun getAllFavorites(): Flow<List<FavoriteEntity>> = dao.getAllFavorites()

    override suspend fun isFavorite(id: Int): Boolean = dao.isFavorite(id)

}