package com.aliayali.market_baz.presentation.screens.shoppingCart

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.ShoppingCardItem
import com.aliayali.market_baz.ui.theme.CoolSlate
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.MidnightBlue
import com.aliayali.market_baz.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    navController: NavController,
    shoppingCartViewModel: ShoppingCartViewModel = hiltViewModel(),
) {
    val user = shoppingCartViewModel.user.value
    val shoppingCardList = shoppingCartViewModel.shoppingCardList.value
    val totalPrice = shoppingCardList.sumOf { it.price * it.number }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightBlue)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    null,
                    modifier = Modifier
                        .background(White.copy(alpha = 0.1f), CircleShape)
                        .padding(9.dp)
                        .clickable {
                            navController.navigate(NavigationScreen.Home.route) {
                                popUpTo(NavigationScreen.Home.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                    tint = White
                )
                Text(
                    text = "سبد خرید", color = White, style = MaterialTheme.typography.titleLarge
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(shoppingCardList) { item ->
                    ShoppingCardItem(
                        item,
                        onDecrease = {
                            shoppingCartViewModel.decreaseItem(item)
                        },
                        onIncrease = {
                            shoppingCartViewModel.increaseItem(item)
                        },
                        onDelete = {
                            shoppingCartViewModel.deleteItem(item)
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
                .background(White)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "آدرس تحویل",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    color = CoolSlate
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = user?.address ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            IceMist,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(all = 12.dp),
                    textAlign = TextAlign.End,
                    color = Color(0xFF32343E),
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "%,d".format(totalPrice)
                    )
                    Text(
                        text = ":مجموع",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "ثبت سفارش",
                        fontSize = 20.sp
                    )
                }
            }
        }

    }

}