package com.aliayali.market_baz.core.utils

fun isValidPhoneNumber(phone: String): Boolean {
    val regex = Regex("^09\\d{9}$")
    return regex.matches(phone)
}

fun normalizePhoneNumber(input: String): String {
    return if (input.startsWith("9") && input.length == 10) {
        "0$input"
    } else {
        input
    }
}