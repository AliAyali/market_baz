package com.aliayali.market_baz.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.aliayali.market_baz.R
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.ProfileItem
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val user = profileViewModel.user.value
    var alertDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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
                        navController.navigate(NavigationScreen.Home.route) {
                            popUpTo(NavigationScreen.Home.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
            )
            Text(
                text = "پروفایل",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.height(16.dp))

        user?.let {
            Image(
                rememberAsyncImagePainter(model = user.image),
                null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .size(150.dp),
                alignment = Alignment.Center
            )
        }

        Spacer(Modifier.height(40.dp))

        ProfileItem(
            R.drawable.personal,
            "اطلاعات شخصی",
            BrightOrange
        ) {
            navController.navigate(NavigationScreen.PersonalInformation.route) {
                popUpTo(NavigationScreen.Profile.route) { inclusive = false }
                launchSingleTop = true
            }
        }

        Spacer(Modifier.height(10.dp))

        ProfileItem(
            R.drawable.map,
            "آدرس",
            Color(0xFF413DFB)
        ) {
            navController.navigate(NavigationScreen.Address.route) {
                popUpTo(NavigationScreen.Profile.route) { inclusive = false }
                launchSingleTop = true
            }
        }

        Spacer(Modifier.height(10.dp))

        ProfileItem(
            R.drawable.card,
            "سبد خرید",
            Color(0xFF369BFF)
        ) {
            navController.navigate(NavigationScreen.ShoppingCart.route) {
                popUpTo(NavigationScreen.Profile.route) { inclusive = false }
                launchSingleTop = true
            }
        }

        Spacer(Modifier.height(10.dp))

        ProfileItem(
            R.drawable.favorite,
            "علاقه مندی ها",
            Color(0xFFB33DFB)
        ) {
            navController.navigate(NavigationScreen.Favorite.route) {
                popUpTo(NavigationScreen.Profile.route) { inclusive = false }
                launchSingleTop = true
            }
        }

        Spacer(Modifier.height(10.dp))

        ProfileItem(
            R.drawable.logout,
            "خروج از حساب",
            Color(0xFFFB4A59)
        ) {
            alertDialog = true
        }

        if (alertDialog)
            AlertDialog(
                onDismissRequest = { alertDialog = false },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextButton(
                            onClick = {
                                alertDialog = false
                                profileViewModel.loginState(false)
                                navController.navigate(NavigationScreen.Login.route) {
                                    popUpTo(NavigationScreen.Profile.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        ) {
                            Text(
                                text = "بله"
                            )
                        }
                        TextButton(
                            onClick = {
                                alertDialog = false
                            }
                        ) {
                            Text(
                                text = "خیر"
                            )
                        }
                    }
                },
                title = {
                    Text(
                        text = "خروج از حساب",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
                text = {
                    Text(
                        text = "آیا میخواهید از حساب خارج شوید؟",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
                icon = {
                    Icon(
                        painterResource(R.drawable.logout),
                        null,
                        tint = Color(0xFFFB4A59),
                        modifier = Modifier.size(20.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp)
            )

    }
}