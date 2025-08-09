package com.aliayali.market_baz.data.fakeData

import com.aliayali.market_baz.R
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.model.ProductCategory

class fk {
    companion object {
        val product = listOf(
            ProductEntity(
                0,
                imageUrl = R.drawable.ic_drink,
                name = "بستنی",
                description = "",
                price = 20000,
                discount = 10,
                star = 3.8,
                categoryId = ProductCategory.CLEANING.id,
                inventory = 10
            ),
            ProductEntity(
                1,
                imageUrl = R.drawable.ic_dairy,
                name = "پفک شیرازی",
                description = "",
                price = 30000,
                discount = 0,
                star = 3.8,
                categoryId = ProductCategory.CLEANING.id,
                inventory = 56
            ),
            ProductEntity(
                0,
                imageUrl = R.drawable.ic_canned,
                name = "بستنی",
                description = "",
                price = 20000,
                discount = 50,
                star = 3.8,
                categoryId = ProductCategory.CLEANING.id,
                inventory = 10
            ),
            ProductEntity(
                0,
                imageUrl = R.drawable.ic_cleaning,
                name = "بستنی",
                description = "",
                price = 20000,
                discount = 90,
                star = 3.8,
                categoryId = ProductCategory.CLEANING.id,
                inventory = 10
            ),
        )
    }
}