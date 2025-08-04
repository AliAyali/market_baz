package com.aliayali.market_baz.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aliayali.market_baz.presentation.screen.splash.SplashScreen

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
            SplashScreen()
        }

    }

}