package com.aliayali.market_baz.presentation.screens.address

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.AddressItem
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun AddressScreen(
    navController: NavController,
    mapViewModel: AddressViewModel = hiltViewModel(),
) {

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
                items(5) {
                    AddressItem(
                        "عنوان",
                        "آدرس",
                        onEdite = {},
                        onDelete = {},
                        onClick = {}
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {},
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

}