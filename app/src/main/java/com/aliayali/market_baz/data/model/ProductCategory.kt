package com.aliayali.market_baz.data.model

import com.aliayali.market_baz.R

enum class ProductCategory(val id: Int, val displayName: String, val iconResId: Int) {
    ALL(0, "همه", R.drawable.ic_all),
    BREAD(1, "نان و غلات", R.drawable.ic_bread),
    MEAT(2, "پروتئین و گوشت", R.drawable.ic_beef),
    DAIRY(3, "لبنیات", R.drawable.ic_dairy),
    SNACK(4, "تنقلات و شیرینی", R.drawable.ic_snack),
    CANNED(5, "کنسرو و غذای آماده", R.drawable.ic_canned),
    CLEANING(6, "مواد شوینده و بهداشتی", R.drawable.ic_cleaning),
    BABY(7, "کودک و نوزاد", R.drawable.ic_baby),
    SPICE(8, "چاشنی و ادویه جات", R.drawable.ic_spice),
    DRINK(9, "نوشیدنی ها", R.drawable.ic_drink),
    BREAKFAST(10, "صبحانه", R.drawable.ic_breakfast);
}


