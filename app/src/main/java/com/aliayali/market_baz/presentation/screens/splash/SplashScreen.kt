package com.aliayali.market_baz.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.R
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.ui.theme.BrightOrange

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    val uiState by splashViewModel.uiState.collectAsState()
    val hasInternet by splashViewModel.hasInternet.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        splashViewModel.checkInternet(context)
        splashViewModel.startDelayOnce(1000L)
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            println("Splash error: $it")
            splashViewModel.clearError()
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.splash_top),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopStart)
        )
        Image(
            painter = painterResource(R.drawable.splash_bottom),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(R.drawable.logo_app),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!uiState.isDelayFinished && hasInternet) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (!hasInternet && uiState.isDelayFinished) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "اینترنت وصل نیست", color = Color.Red, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            splashViewModel.retry(context)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrightOrange
                        ),
                        shape = CircleShape
                    ) {
                        Text("تلاش دوباره")
                    }
                }
            }
        }
    }

    LaunchedEffect(uiState.isDelayFinished, hasInternet, uiState.user, uiState.isLoggedIn) {
        if (uiState.isDelayFinished && hasInternet) {
            when {
                uiState.isLoggedIn && (uiState.user as? com.aliayali.market_baz.data.local.database.entity.UserEntity)?.isAdmin == true -> {
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