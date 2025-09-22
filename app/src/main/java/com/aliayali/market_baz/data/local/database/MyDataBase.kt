package com.aliayali.market_baz.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliayali.market_baz.data.local.database.MyDataBase.Companion.DATABASE_VERSION
import com.aliayali.market_baz.data.local.database.dao.AddressDao
import com.aliayali.market_baz.data.local.database.dao.CommentDao
import com.aliayali.market_baz.data.local.database.dao.FavoriteDao
import com.aliayali.market_baz.data.local.database.dao.OrderDao
import com.aliayali.market_baz.data.local.database.dao.ProductDao
import com.aliayali.market_baz.data.local.database.dao.RatingDao
import com.aliayali.market_baz.data.local.database.dao.ShoppingCardDao
import com.aliayali.market_baz.data.local.database.dao.UserDao
import com.aliayali.market_baz.data.local.database.entity.AddressEntity
import com.aliayali.market_baz.data.local.database.entity.CommentEntity
import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import com.aliayali.market_baz.data.local.database.entity.OrderEntity
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.RatingEntity
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        ShoppingCardEntity::class,
        FavoriteEntity::class,
        AddressEntity::class,
        CommentEntity::class,
        RatingEntity::class,
        OrderEntity::class
    ],
    version = DATABASE_VERSION
)
abstract class MyDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun shoppingCardDao(): ShoppingCardDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun addressDao(): AddressDao
    abstract fun commentDao(): CommentDao
    abstract fun ratingDao(): RatingDao
    abstract fun orderDao(): OrderDao

    companion object {
        const val DATABASE_NAME = "market_db"
        const val USER_TABLE = "user_table"
        const val PRODUCT_TABLE = "product_table"
        const val SHOPPING_CARD_TABLE = "shopping_card_table"
        const val FAVORITE_TABLE = "favorites_table"
        const val ADDRESS_TABLE = "address_table"
        const val COMMENT_TABLE = "comment_table"
        const val RATING_TABLE = "rating_table"
        const val ORDER_TABLE = "order_table"
        const val DATABASE_VERSION = 12
    }
}