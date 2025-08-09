package com.aliayali.market_baz.di

import android.content.Context
import androidx.room.Room
import com.aliayali.market_baz.data.local.database.MyDataBase
import com.aliayali.market_baz.data.local.database.dao.ProductDao
import com.aliayali.market_baz.data.local.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): MyDataBase {
        return Room.databaseBuilder(
            context,
            MyDataBase::class.java,
            MyDataBase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(database: MyDataBase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideProductDao(database: MyDataBase): ProductDao {
        return database.productDao()
    }

}