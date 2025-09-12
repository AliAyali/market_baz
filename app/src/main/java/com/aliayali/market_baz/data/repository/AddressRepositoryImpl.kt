package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.AddressDao
import com.aliayali.market_baz.data.local.database.entity.AddressEntity
import com.aliayali.market_baz.domain.repository.AddressRepository
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val dao: AddressDao,
) : AddressRepository {
    override suspend fun insertAddress(address: AddressEntity) {
        dao.insertAddress(address)
    }

    override suspend fun updateAddress(address: AddressEntity) {
        dao.updateAddress(address)
    }

    override suspend fun deleteAddress(address: AddressEntity) {
        dao.deleteAddress(address)
    }

    override suspend fun getAddressById(addressId: Int) = dao.getAddressById(addressId)

    override fun getAllAddresses() = dao.getAllAddresses()
}