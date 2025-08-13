package com.aliayali.market_baz.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aliayali.market_baz.R
import com.aliayali.market_baz.data.local.database.MyDataBase
import com.aliayali.market_baz.data.local.database.dao.ProductDao
import com.aliayali.market_baz.data.local.database.dao.ShoppingCardDao
import com.aliayali.market_baz.data.local.database.dao.UserDao
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val tempDb = Room.databaseBuilder(
                            context,
                            MyDataBase::class.java,
                            MyDataBase.DATABASE_NAME
                        ).build()
                        tempDb.userDao().insertUser(
                            UserEntity(
                                phone = "09000000000",
                                image = R.drawable.user,
                                name = "admin",
                                password = "1111",
                                isAdmin = true
                            )
                        )
                    }
                }
            })
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

    @Provides
    fun provideShoppingCardDao(database: MyDataBase): ShoppingCardDao {
        return database.shoppingCardDao()
    }

}