package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.FavoriteDao
import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import com.aliayali.market_baz.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao,
) : FavoriteRepository {

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        dao.insertFavorite(favorite)
    }

    override suspend fun deleteFavoriteByProductId(id: String, userPhone: String) {
        dao.deleteFavoriteByProductId(id, userPhone)
    }

    override suspend fun deleteFavorite(favorite: FavoriteEntity) {
        dao.deleteFavorite(favorite)
    }

    override fun getAllFavorites(userPhone: String): Flow<List<FavoriteEntity>> {
        return dao.getAllFavorites(userPhone)
            .map { list -> list.filter { it.userPhone == userPhone } }
    }

    override suspend fun isFavorite(id: String, userPhone: String): Boolean {
        val list = dao.getAllFavorites(userPhone).first()
        return list.any { it.productId == id && it.userPhone == userPhone }
    }
}
