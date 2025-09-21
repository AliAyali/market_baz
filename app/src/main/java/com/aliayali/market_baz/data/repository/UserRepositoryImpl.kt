package com.aliayali.market_baz.data.repository

import com.aliayali.market_baz.data.local.database.dao.UserDao
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao,
) : UserRepository {

    override suspend fun insertUser(user: UserEntity) {
        dao.insertUser(user)
    }

    override suspend fun getUserByPhone(phone: String): UserEntity? {
        return dao.getUserByPhone(phone)
    }

    override suspend fun updateUser(user: UserEntity) {
        dao.updateUser(user)
    }

    override suspend fun getAllUsers(): List<UserEntity> =
        dao.getAllUsers()

    override suspend fun deleteUserByPhone(phone: String) {
        dao.deleteUserByPhone(phone)
    }

    override suspend fun getUsersCount(): Int = dao.getUsersCount()
}