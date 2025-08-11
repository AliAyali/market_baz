package com.aliayali.market_baz.core.utils

import java.text.NumberFormat
import java.util.Locale

fun formatPrice(price: Int): String {
    val iranLocale = Locale.forLanguageTag("fa-IR")
    val formatter = NumberFormat.getNumberInstance(iranLocale)
    return formatter.format(price)
}