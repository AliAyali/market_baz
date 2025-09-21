package com.aliayali.market_baz.presentation.screens.admin.usersSection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.screens.admin.components.UserItem
import com.aliayali.market_baz.presentation.ui.SearchTextField

@Composable
fun UsersSection(
    navController: NavController,
    usersViewModel: UsersViewModel = hiltViewModel(),
) {
    var searchQuery by remember { mutableStateOf("") }
    val users by usersViewModel.users.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        usersViewModel.loadAllUsers()
    }

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
                text = "مدیریت کاربران",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.height(8.dp))

        SearchTextField(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                usersViewModel.searchUsers(it)
            }
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(users) { user: UserEntity ->
                UserItem(
                    user = user,
                    onClick = {
                        navController.navigate(NavigationScreen.User.createRoute(user.phone)) {
                            popUpTo(NavigationScreen.User.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}