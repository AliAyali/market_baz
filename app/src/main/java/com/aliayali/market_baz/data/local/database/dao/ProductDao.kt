package com.aliayali.market_baz.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: ProductEntity)

    @Query("SELECT * FROM product_table WHERE discount > 0")
    suspend fun getProductsWithDiscount(): List<ProductEntity>

    @Query("SELECT * FROM product_table WHERE categoryId = :categoryId ORDER BY star DESC")
    fun getProductsByCategorySortedByStar(categoryId: Int): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product_table WHERE id = :productId LIMIT 1")
    suspend fun getProductById(productId: Int): ProductEntity?

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)
}