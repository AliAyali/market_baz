package com.aliayali.market_baz.presentation.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.FavoriteItem
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
) {
    val favorites = favoriteViewModel.favorite.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                null,
                modifier = Modifier
                    .background(IceMist, CircleShape)
                    .padding(9.dp)
                    .clickable {
                        navController.navigate(NavigationScreen.Profile.route) {
                            popUpTo(NavigationScreen.Profile.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
            )
            Text(
                text = "علاقه مندی ها",
                style = MaterialTheme.typography.titleLarge
            )
        }

        LazyVerticalGrid(
            modifier = Modifier.padding(top = 16.dp),
            columns = GridCells.Adaptive(minSize = 160.dp)
        ) {
            items(favorites) {
                FavoriteItem(
                    it,
                ) {
                    navController.navigate(
                        NavigationScreen.Product.createRoute(it?.productId ?: 0)
                    )
                }
            }
        }
    }
}