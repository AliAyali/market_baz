package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.ShoppingCardDao
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingCardRepositoryImpl @Inject constructor(
    private val dao: ShoppingCardDao,
) : ShoppingCardRepository {
    override suspend fun insertItem(item: ShoppingCardEntity) {
        dao.insertItem(item)
    }

    override suspend fun updateItem(item: ShoppingCardEntity) {
        dao.updateItem(item)
    }

    override suspend fun deleteItem(item: ShoppingCardEntity) {
        dao.deleteItem(item)
    }

    override fun getAllItems(): Flow<List<ShoppingCardEntity>> = dao.getAllItems()

    override suspend fun getItemByProductId(productId: Int): ShoppingCardEntity? =
        dao.getItemByProductId(productId)
}