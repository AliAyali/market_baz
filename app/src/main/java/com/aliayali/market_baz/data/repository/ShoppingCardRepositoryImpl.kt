package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.ShoppingCardDao
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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

    override fun getAllItems(userPhone: String): Flow<List<ShoppingCardEntity>> {
        return dao.getAllItems(userPhone).map { list -> list.filter { it.userPhone == userPhone } }
    }

    override suspend fun getItemByProductId(
        productId: Int,
        userPhone: String,
    ): ShoppingCardEntity? {
        val list = dao.getAllItems(userPhone).first()
        return list.firstOrNull { it.productId == productId && it.userPhone == userPhone }
    }
}
