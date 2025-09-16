package com.aliayali.market_baz.presentation.screens.admin.reportsSection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aliayali.market_baz.navigation.NavigationScreen

@Composable
fun ReportsSection(
    navController: NavController,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.navigate(NavigationScreen.Home.route) {
                        popUpTo(NavigationScreen.Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    Icons.Default.Home,
                    "Home",
                    Modifier.size(28.dp)
                )
            }
            Text(
                text = "گزارشات",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}