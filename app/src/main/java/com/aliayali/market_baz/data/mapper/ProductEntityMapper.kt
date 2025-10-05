package com.aliayali.market_baz.data.mapper

import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id.toString(),
        imageUrl = this.imageUrl,
        name = this.name,
        description = this.description,
        price = this.price,
        discount = this.discount,
        star = this.star,
        categoryId = this.categoryId,
        inventory = this.inventory,
        numberOfComments = this.numberOfComments,
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id?.toIntOrNull() ?: 0,
        imageUrl = this.imageUrl,
        name = this.name,
        description = this.description,
        price = this.price,
        discount = this.discount,
        star = this.star,
        categoryId = this.categoryId,
        inventory = this.inventory,
        numberOfComments = this.numberOfComments,
    )
}
