package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.ProductDao
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
) : ProductRepository {
    override suspend fun insertProducts(products: ProductEntity) {
        dao.insertProducts(products)
    }

    override suspend fun getProductsWithDiscount(): List<ProductEntity> =
        dao.getProductsWithDiscount()

    override fun getProductsByCategorySortedByStar(categoryId: Int): Flow<List<ProductEntity>> =
        dao.getProductsByCategorySortedByStar(categoryId)

    override suspend fun getProductById(productId: Int): ProductEntity? =
        dao.getProductById(productId)

    override fun getAllProducts(): Flow<List<ProductEntity>> = dao.getAllProducts()

    override suspend fun updateProduct(product: ProductEntity) {
        dao.updateProduct(product)
    }

    override suspend fun deleteProduct(product: ProductEntity) {
        dao.deleteProduct(product)
    }

    override suspend fun getProductsCount(): Int = dao.getProductsCount()
}