package com.aliayali.market_baz.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliayali.market_baz.data.local.database.MyDataBase.Companion.DATABASE_VERSION
import com.aliayali.market_baz.data.local.database.dao.UserDao
import com.aliayali.market_baz.data.local.database.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
    ],
    version = DATABASE_VERSION
)
abstract class MyDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "market_db"
        const val USER_TABLE = "user_table"
        const val DATABASE_VERSION = 1
    }
}