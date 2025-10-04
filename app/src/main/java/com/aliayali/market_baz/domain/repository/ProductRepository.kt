package com.aliayali.market_baz.domain.repository

import com.aliayali.market_baz.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProducts(product: Product)
    suspend fun getProductsWithDiscount(): List<Product>
    fun getProductsByCategorySortedByStar(categoryId: Int): Flow<List<Product>>
    suspend fun getProductById(productId: String): Product?
    fun getAllProducts(): Flow<List<Product>>
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun getProductsCount(): Int
}