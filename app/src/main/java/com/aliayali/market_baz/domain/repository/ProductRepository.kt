package com.aliayali.market_baz.domain.repository

import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProducts(products: ProductEntity)
    suspend fun getProductsWithDiscount(): List<ProductEntity>
    suspend fun getProductsByCategorySortedByStar(categoryId: Int): List<ProductEntity>
    suspend fun getProductById(productId: Int): ProductEntity?
    fun getAllProducts(): Flow<List<ProductEntity>>
    suspend fun updateProduct(product: ProductEntity)
    suspend fun deleteProduct(product: ProductEntity)
}