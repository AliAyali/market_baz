package com.aliayali.market_baz.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aliayali.market_baz.data.local.database.entity.AddressEntity
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: AddressEntity)

    @Update
    suspend fun updateAddress(address: AddressEntity)

    @Delete
    suspend fun deleteAddress(address: AddressEntity)

    @Query("SELECT * FROM address_table WHERE id = :addressId LIMIT 1")
    suspend fun getAddressById(addressId: Int): AddressEntity?

    @Query("SELECT * FROM address_table")
    fun getAllAddresses(): Flow<List<AddressEntity>>

}