package com.aliayali.market_baz.navigation

sealed class NavigationScreen(val route: String) {
    object Splash : NavigationScreen("splash")
    object Login : NavigationScreen("login")
    object Signup : NavigationScreen("signup")
    object ForgotPassword : NavigationScreen("forgotPassword")
    object Verification : NavigationScreen("verification")
    object Home : NavigationScreen("home")
    object Admin : NavigationScreen("admin")
    object Product : NavigationScreen("product") {
        fun createRoute(productId: Int) = "$route/$productId"
    }

    object Profile : NavigationScreen("profile")
    object PersonalInformation : NavigationScreen("personalInformation")
    object ShoppingCart : NavigationScreen("shoppingCart")
    object Favorite : NavigationScreen("favorite")
    object Address : NavigationScreen("address")
    object AddNewAddress : NavigationScreen("addNewAddress") {
        fun createRoute(addressId: Int) = "$route/$addressId"
    }

    object AddProduct : NavigationScreen("addProduct") {
        fun createRoute(productId: Int) = "$route/$productId"
    }
}