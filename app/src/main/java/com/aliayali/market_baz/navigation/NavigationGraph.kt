package com.aliayali.market_baz.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aliayali.market_baz.presentation.screen.forgotPassword.ForgotPasswordScreen
import com.aliayali.market_baz.presentation.screen.login.LoginScreen
import com.aliayali.market_baz.presentation.screen.signup.SignupScreen
import com.aliayali.market_baz.presentation.screen.splash.SplashScreen
import com.aliayali.market_baz.presentation.screen.verification.VerificationScreen

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

    }

}