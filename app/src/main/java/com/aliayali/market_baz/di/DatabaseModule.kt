package com.aliayali.market_baz.di

import android.content.Context
import androidx.room.Room
import com.aliayali.market_baz.data.local.database.MyDataBase
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
            .build()
    }

    @Provides
    fun provideNoteDao(database: MyDataBase): UserDao {
        return database.userDao()
    }

}