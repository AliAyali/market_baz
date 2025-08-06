package com.aliayali.market_baz.data.model

import com.aliayali.market_baz.R

data class CategoryDto(
    val id: Int,
    val name: String,
    val iconResId: Int? = null,
    var selected: Boolean = false,
) {
    companion object {
        val categoryDto = listOf(
            CategoryDto(0, "همه", R.drawable.ic_all, true),
            CategoryDto(1, "نان و غلات", R.drawable.ic_bread),
            CategoryDto(2, "پروتئین و گوشت", R.drawable.ic_beef),
            CategoryDto(3, "لبنیات", R.drawable.ic_dairy),
            CategoryDto(4, "تنقلات و شیرینی", R.drawable.ic_snack),
            CategoryDto(5, "کنسرو و غذای آماده", R.drawable.ic_canned),
            CategoryDto(6, "مواد شوینده و بهداشتی", R.drawable.ic_cleaning),
            CategoryDto(7, "کودک و نوزاد", R.drawable.ic_baby),
            CategoryDto(8, "چاشنی و ادویه جات", R.drawable.ic_spice),
            CategoryDto(9, "نوشیدنی ها", R.drawable.ic_drink),
            CategoryDto(10, "صبحانه", R.drawable.ic_breakfast),
        )
    }
}
