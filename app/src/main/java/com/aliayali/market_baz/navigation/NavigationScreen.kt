package com.aliayali.market_baz.navigation

sealed class NavigationScreen(val route: String) {
    object Splash : NavigationScreen("splash")
    object Login : NavigationScreen("login")
    object Signup : NavigationScreen("signup")
    object ForgotPassword : NavigationScreen("forgotPassword")
    object Verification : NavigationScreen("verification")
    object Home : NavigationScreen("home")
    object Admin : NavigationScreen("admin")
}