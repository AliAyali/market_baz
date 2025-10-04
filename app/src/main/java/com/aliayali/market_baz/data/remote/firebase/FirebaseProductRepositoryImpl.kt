package com.aliayali.market_baz.data.remote.firebase

import com.aliayali.market_baz.domain.model.Product
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

    override suspend fun insertProducts(product: Product) {
        val docRef = productsCollection.add(product).await()
        val generatedId = docRef.id
        docRef.update("id", generatedId).await()
    }

    override suspend fun getProductsWithDiscount(): List<Product> {
        val snapshot = productsCollection
            .whereGreaterThan("discount", 0)
            .get()
            .await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(Product::class.java)?.copy(id = doc.id)
        }
    }

    override fun getProductsByCategorySortedByStar(categoryId: Int): Flow<List<Product>> = flow {
        val snapshot = productsCollection
            .whereEqualTo("categoryId", categoryId)
            .orderBy("star", Query.Direction.DESCENDING)
            .get()
            .await()
        emit(snapshot.documents.mapNotNull { doc ->
            doc.toObject(Product::class.java)?.copy(id = doc.id)
        })
    }

    override suspend fun getProductById(productId: String): Product? {
        val doc = productsCollection.document(productId).get().await()
        return doc.toObject(Product::class.java)?.copy(id = doc.id)
    }

    override fun getAllProducts(): Flow<List<Product>> = flow {
        val snapshot = productsCollection.get().await()
        emit(snapshot.documents.mapNotNull { doc ->
            doc.toObject(Product::class.java)?.copy(id = doc.id)
        })
    }

    override suspend fun updateProduct(product: Product) {
        product.id?.let {
            productsCollection.document(it).set(product).await()
        }
    }

    override suspend fun deleteProduct(product: Product) {
        product.id?.let {
            productsCollection.document(it).delete().await()
        }
    }

    override suspend fun getProductsCount(): Int {
        val snapshot = productsCollection.get().await()
        return snapshot.size()
    }
}