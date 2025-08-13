package com.aliayali.market_baz.domain.repository

import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import kotlinx.coroutines.flow.Flow

interface ShoppingCardRepository {
    suspend fun insertItem(item: ShoppingCardEntity)
    suspend fun updateItem(item: ShoppingCardEntity)
    suspend fun deleteItem(item: ShoppingCardEntity)
    fun getAllItems(): Flow<List<ShoppingCardEntity>>
    suspend fun getItemByProductId(productId: Int): ShoppingCardEntity?
}