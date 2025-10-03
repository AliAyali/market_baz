package com.aliayali.market_baz.data.remote.firebase

import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseProductRepositoryImpl @Inject constructor(
    firestore: FirebaseFirestore,
) : ProductRepository {
    private val productsCollection = firestore.collection("products")

    override suspend fun insertProducts(products: ProductEntity) {
        val id = products.id.toString()
        productsCollection.document(id).set(products).await()
    }

    override suspend fun getProductsWithDiscount(): List<ProductEntity> {
        val snapshot = productsCollection
            .whereGreaterThan("discount", 0)
            .get()
            .await()
        return snapshot.toObjects(ProductEntity::class.java)
    }

    override fun getProductsByCategorySortedByStar(categoryId: Int): Flow<List<ProductEntity>> =
        flow {
            val snapshot = productsCollection
                .whereEqualTo("categoryId", categoryId)
                .orderBy("star", Query.Direction.DESCENDING)
                .get()
                .await()
            emit(snapshot.toObjects(ProductEntity::class.java))
        }

    override suspend fun getProductById(productId: Int): ProductEntity? {
        val doc = productsCollection.document(productId.toString()).get().await()
        return doc.toObject(ProductEntity::class.java)
    }

    override fun getAllProducts(): Flow<List<ProductEntity>> = flow {
        val snapshot = productsCollection.get().await()
        emit(snapshot.toObjects(ProductEntity::class.java))
    }

    override suspend fun updateProduct(product: ProductEntity) {
        productsCollection.document(product.id.toString()).set(product).await()
    }

    override suspend fun deleteProduct(product: ProductEntity) {
        productsCollection.document(product.id.toString()).delete().await()
    }

    override suspend fun getProductsCount(): Int {
        val snapshot = productsCollection.get().await()
        return snapshot.size()
    }
}