package com.aliayali.market_baz.di

import com.aliayali.market_baz.data.repository.AddressRepositoryImpl
import com.aliayali.market_baz.data.repository.CommentRepositoryImpl
import com.aliayali.market_baz.data.repository.FavoriteRepositoryImpl
import com.aliayali.market_baz.data.repository.OrderRepositoryImpl
import com.aliayali.market_baz.data.repository.ProductRepositoryImpl
import com.aliayali.market_baz.data.repository.RatingRepositoryImpl
import com.aliayali.market_baz.data.repository.ShoppingCardRepositoryImpl
import com.aliayali.market_baz.data.repository.UserRepositoryImpl
import com.aliayali.market_baz.domain.repository.AddressRepository
import com.aliayali.market_baz.domain.repository.CommentRepository
import com.aliayali.market_baz.domain.repository.FavoriteRepository
import com.aliayali.market_baz.domain.repository.OrderRepository
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.RatingRepository
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl,
    ): ProductRepository

    @Binds
    abstract fun bindShoppingCardRepository(
        shoppingCardRepositoryImpl: ShoppingCardRepositoryImpl,
    ): ShoppingCardRepository

    @Binds
    abstract fun bindFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl,
    ): FavoriteRepository

    @Binds
    abstract fun bindAddressRepository(
        addressRepositoryImpl: AddressRepositoryImpl,
    ): AddressRepository

    @Binds
    abstract fun bindCommentRepository(
        commentRepositoryImpl: CommentRepositoryImpl,
    ): CommentRepository

    @Binds
    abstract fun bindRatingRepository(
        ratingRepositoryImpl: RatingRepositoryImpl,
    ): RatingRepository

    @Binds
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl,
    ): OrderRepository
}
