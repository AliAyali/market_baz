package com.aliayali.market_baz.presentation.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.screens.admin.components.AdminSwitchRow
import com.aliayali.market_baz.presentation.screens.admin.components.InfoRow
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.Red

@Composable
fun UserScreen(
    navController: NavController,
    userPhone: String,
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val user by userViewModel.user.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(userPhone) {
        userViewModel.getUserByPhone(userPhone)
    }

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
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .background(IceMist, CircleShape)
                    .padding(9.dp)
                    .clickable {
                        navController.navigate(NavigationScreen.Admin.route) {
                            popUpTo(NavigationScreen.Admin.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
            )
            Text(
                text = "جزئیات کاربر",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        user?.let { u ->
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(IceMist),
                contentAlignment = Alignment.Center
            ) {
                if (u.image.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(u.image),
                        contentDescription = u.name ?: "User",
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                            .background(IceMist)
                    )
                } else {
                    Text(
                        text = u.name?.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = IceMist)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow(title = "نام", value = u.name ?: "-")
                    Spacer(Modifier.height(10.dp))
                    InfoRow(title = "شماره موبایل", value = u.phone)
                    Spacer(Modifier.height(10.dp))
                    InfoRow(title = "آدرس", value = u.address)
                    Spacer(Modifier.height(10.dp))
                    AdminSwitchRow(
                        isAdmin = u.isAdmin,
                        onToggle = { userViewModel.toggleAdminStatus(u.phone) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showDeleteDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Red),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "حذف کاربر",
                    fontSize = 18.sp
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "حذف کاربر",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            text = {
                Text(
                    text = "آیا از حذف این کاربر اطمینان دارید؟",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        user?.phone?.let { userViewModel.deleteUser(it) }
                        showDeleteDialog = false
                        navController.popBackStack()
                    }
                ) { Text("بله") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("خیر") }
            }
        )
    }
}


