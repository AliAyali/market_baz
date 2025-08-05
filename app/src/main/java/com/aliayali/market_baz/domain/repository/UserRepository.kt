package com.aliayali.market_baz.domain.repository

import com.aliayali.market_baz.data.local.database.entity.UserEntity

interface UserRepository {

    suspend fun insertUser(user: UserEntity)

    suspend fun getUserByPhone(phone: String): UserEntity?

}