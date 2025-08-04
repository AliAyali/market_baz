package com.aliayali.market_baz.navigation

sealed class NavigationScreen(val route: String) {
    object Splash : NavigationScreen("splash")

}