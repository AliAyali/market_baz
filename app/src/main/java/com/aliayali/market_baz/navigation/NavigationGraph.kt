package com.aliayali.market_baz.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aliayali.market_baz.presentation.screens.admin.AdminScreen
import com.aliayali.market_baz.presentation.screens.favorite.FavoriteScreen
import com.aliayali.market_baz.presentation.screens.forgotPassword.ForgotPasswordScreen
import com.aliayali.market_baz.presentation.screens.home.HomeScreen
import com.aliayali.market_baz.presentation.screens.login.LoginScreen
import com.aliayali.market_baz.presentation.screens.address.AddressScreen
import com.aliayali.market_baz.presentation.screens.personalInformation.PersonalInformationScreen
import com.aliayali.market_baz.presentation.screens.product.ProductScreen
import com.aliayali.market_baz.presentation.screens.profile.ProfileScreen
import com.aliayali.market_baz.presentation.screens.shoppingCart.ShoppingCartScreen
import com.aliayali.market_baz.presentation.screens.signup.SignupScreen
import com.aliayali.market_baz.presentation.screens.splash.SplashScreen
import com.aliayali.market_baz.presentation.screens.verification.VerificationScreen

@Composable
fun SetupNavigation(
    padding: PaddingValues,
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = NavigationScreen.Splash.route,
        modifier = Modifier.padding(padding)
    ) {

        composable(
            route = NavigationScreen.Splash.route
        ) {
            SplashScreen(navController)
        }

        composable(
            route = NavigationScreen.Login.route
        ) {
            LoginScreen(navController)
        }

        composable(
            route = NavigationScreen.Signup.route
        ) {
            SignupScreen(navController)
        }

        composable(
            route = NavigationScreen.ForgotPassword.route
        ) {
            ForgotPasswordScreen(navController)
        }

        composable(
            route = NavigationScreen.Verification.route
        ) {
            VerificationScreen(navController)
        }

        composable(
            route = NavigationScreen.Home.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = NavigationScreen.Admin.route
        ) {
            AdminScreen(navController)
        }

        composable(
            route = NavigationScreen.Product.route + "/{productId}"
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0
            ProductScreen(
                navController,
                productId = productId
            )
        }

        composable(
            route = NavigationScreen.Profile.route
        ) {
            ProfileScreen(navController)
        }

        composable(
            route = NavigationScreen.PersonalInformation.route
        ) {
            PersonalInformationScreen(navController)
        }

        composable(
            route = NavigationScreen.ShoppingCart.route
        ) {
            ShoppingCartScreen(navController)
        }

        composable(
            route = NavigationScreen.Favorite.route
        ) {
            FavoriteScreen(navController)
        }

        composable(
            route = NavigationScreen.Address.route
        ) {
            AddressScreen(navController)
        }
    }

}