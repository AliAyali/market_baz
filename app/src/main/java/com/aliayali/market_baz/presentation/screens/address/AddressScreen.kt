package com.aliayali.market_baz.presentation.screens.address

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.data.local.database.entity.AddressEntity
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.AddressItem
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun AddressScreen(
    navController: NavController,
    addressViewModel: AddressViewModel = hiltViewModel(),
) {
    val addresses by addressViewModel.addresses.collectAsState(initial = emptyList())
    var alertDialog by remember { mutableStateOf(false) }
    var addressToDelete by remember { mutableStateOf<AddressEntity?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
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
                    text = "آدرس های من",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            LazyColumn(
                modifier = Modifier.padding(top = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(addresses) {
                    AddressItem(
                        it.name,
                        "${it.city} - ${it.street} - ${it.milan} - پلاک ${it.plate} - طبقه ${it.floor}",
                        onEdite = {
                            navController.navigate(NavigationScreen.AddNewAddress.createRoute(it.id)) {
                                popUpTo(NavigationScreen.Profile.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onDelete = {
                            addressToDelete = it
                            alertDialog = true
                        },
                        onClick = {
                            addressViewModel.updateUserAddress(
                                "${it.city} - ${it.street} - ${it.milan} - پلاک ${it.plate} - طبقه ${it.floor}"
                            )
                            navController.navigate(NavigationScreen.Profile.route) {
                                popUpTo(NavigationScreen.Profile.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
                item {
                    Spacer(Modifier.height(50.dp))
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    navController.navigate(NavigationScreen.AddNewAddress.createRoute(0)) {
                        popUpTo(NavigationScreen.Address.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "افزودن آدرس جدید",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

    }

    if (alertDialog && addressToDelete != null)
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
                            addressToDelete?.let { addressViewModel.deleteAddress(it) }
                            alertDialog = false
                            addressToDelete = null
                        }
                    ) { Text(text = "بله") }

                    TextButton(
                        onClick = {
                            alertDialog = false
                            addressToDelete = null
                        }
                    ) { Text(text = "خیر") }
                }
            },
            title = {
                Text(
                    text = "حذف آدرس",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            text = {
                Text(
                    text = "آیا میخواهید آدرس حذف شود؟",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            icon = {
                Icon(
                    Icons.Default.Delete,
                    null,
                    tint = Color(0xFFFB4A59),
                    modifier = Modifier.size(20.dp)
                )
            },
            shape = RoundedCornerShape(10.dp)
        )


}