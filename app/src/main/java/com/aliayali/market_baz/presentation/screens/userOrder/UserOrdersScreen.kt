package com.aliayali.market_baz.presentation.screens.userOrder

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aliayali.market_baz.R
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.screens.admin.components.UserOrderSummaryCard
import com.aliayali.market_baz.presentation.screens.admin.ordersSection.OrdersViewModel
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun UserOrdersScreen(
    navController: NavController,
    userPhone: String,
    ordersViewModel: OrdersViewModel = hiltViewModel(),
) {
    val orders by ordersViewModel.ordersState.collectAsState()
    val userOrders = orders.filter { it.order.userPhone == userPhone }

    if (userOrders.isNotEmpty()) {
        val allProducts = userOrders.joinToString("، ") { it.productNames }
        val totalQuantity = userOrders.sumOf { it.order.quantity }
        val totalPrice = userOrders.sumOf { it.order.totalPrice.toInt() }
        val address = userOrders.first().userAddress
        val status = userOrders.last().order.status

        val lottieRes = when (status) {
            "PENDING" -> R.raw.pending
            "PAID" -> R.raw.paid
            "SHIPPED" -> R.raw.shipped
            "DELIVERED" -> R.raw.delivered
            else -> R.raw.pending
        }

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(lottieRes)
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
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
                            navController.navigate(NavigationScreen.ShoppingCart.route) {
                                popUpTo(NavigationScreen.ShoppingCart.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                )
                Text(
                    text = "سفارش‌های من",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            UserOrderSummaryCard(
                products = allProducts,
                address = address,
                quantity = totalQuantity,
                price = totalPrice,
                status = status
            )

            Button(
                onClick = {
                    ordersViewModel.deleteAllUserOrders(userPhone)

                    navController.navigate(NavigationScreen.Home.route) {
                        popUpTo(NavigationScreen.UserOrders.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                enabled = status == "DELIVERED",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "کالا رو تحویل گرفتم",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "شما هنوز سفارشی ثبت نکردید.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}