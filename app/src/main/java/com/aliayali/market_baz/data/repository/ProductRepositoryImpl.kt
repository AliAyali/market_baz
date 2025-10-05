package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.ProductDao
import com.aliayali.market_baz.domain.model.Product
import com.aliayali.market_baz.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
) : ProductRepository {
    override suspend fun insertProducts(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsWithDiscount(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun getProductsByCategorySortedByStar(categoryId: Int): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(productId: String): Product? {
        TODO("Not yet implemented")
    }

    override fun getAllProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsCount(): Int {
        TODO("Not yet implemented")
    }

}