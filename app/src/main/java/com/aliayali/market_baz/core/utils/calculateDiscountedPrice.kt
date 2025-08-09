package com.aliayali.market_baz.core.utils

fun calculateDiscountedPrice(originalPrice: Int, discountPercent: Int): Int {
    val discountAmount = originalPrice * discountPercent / 100
    return originalPrice - discountAmount
}