package com.aliayali.market_baz.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingCardEntity)

    @Update
    suspend fun updateItem(item: ShoppingCardEntity)

    @Delete
    suspend fun deleteItem(item: ShoppingCardEntity)

    @Query("SELECT * FROM shopping_card_table WHERE userPhone = :userPhone")
    fun getAllItems(userPhone: String): Flow<List<ShoppingCardEntity>>

    @Query("SELECT * FROM shopping_card_table WHERE productId = :productId AND userPhone = :userPhone LIMIT 1")
    suspend fun getItemByProductId(productId: Int, userPhone: String): ShoppingCardEntity?
}
