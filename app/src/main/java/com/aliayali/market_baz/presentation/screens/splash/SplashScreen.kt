package com.aliayali.market_baz.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.R
import com.aliayali.market_baz.navigation.NavigationScreen

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    val uiState by splashViewModel.uiState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painterResource(R.drawable.splash_top),
            null,
            Modifier.align(Alignment.Start)
        )
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(50.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Image(
                painterResource(R.drawable.logo_app),
                null,
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)
            )
        }
        Image(
            painterResource(R.drawable.splash_bottom),
            null,
            Modifier.align(Alignment.End)
        )
    }

    LaunchedEffect(uiState.isDelayFinished, uiState.isLoggedIn, uiState.user) {
        if (uiState.isDelayFinished) {
            when {
                uiState.isLoggedIn && uiState.user?.isAdmin == true -> {
                    navController.navigate(NavigationScreen.Admin.route) {
                        popUpTo(NavigationScreen.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }

                uiState.isLoggedIn -> {
                    navController.navigate(NavigationScreen.Home.route) {
                        popUpTo(NavigationScreen.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }

                else -> {
                    navController.navigate(NavigationScreen.Login.route) {
                        popUpTo(NavigationScreen.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}
