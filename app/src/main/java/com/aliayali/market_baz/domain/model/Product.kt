package com.aliayali.market_baz.domain.model

data class Product(
    val id: String? = null,
    val imageUrl: String = "",
    val name: String = "",
    val description: String? = "",
    val price: Int = 0,
    val discount: Int = 0,
    val star: Double = 0.0,
    val categoryId: Int = 0,
    val inventory: Int = 0,
    val numberOfComments: Int = 0,
)