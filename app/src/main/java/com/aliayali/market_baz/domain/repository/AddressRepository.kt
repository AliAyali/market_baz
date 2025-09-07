package com.aliayali.market_baz.domain.repository

import com.aliayali.market_baz.data.local.database.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun insertAddress(address: AddressEntity)
    suspend fun updateAddress(address: AddressEntity)
    suspend fun deleteAddress(address: AddressEntity)
    suspend fun getAddressById(addressId: Int): AddressEntity?
    fun getAllAddresses(): Flow<List<AddressEntity>>
}