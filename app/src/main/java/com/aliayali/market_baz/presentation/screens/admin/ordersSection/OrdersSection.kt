package com.aliayali.market_baz.presentation.screens.admin.ordersSection

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.EmptyState
import com.aliayali.market_baz.presentation.screens.admin.components.UserCardWithStatus
import com.aliayali.market_baz.presentation.screens.admin.components.showAdminNotification
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersSection(
    navController: NavController,
    ordersViewModel: OrdersViewModel = hiltViewModel(),
) {
    val orders by ordersViewModel.ordersState.collectAsState()
    val ordersByUser = orders.groupBy { it.order.userPhone }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedUserOrders by remember {
        mutableStateOf<List<OrdersViewModel.OrderUiState>>(
            emptyList()
        )
    }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    var oldOrdersCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(orders.size) {
        if (orders.size > oldOrdersCount) {
            context.showAdminNotification("یک سفارش جدید ثبت شد!")
        }
        oldOrdersCount = orders.size
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
                Icon(Icons.Default.Home, contentDescription = "Home", Modifier.size(28.dp))
            }
            Text(
                text = "سفارشات",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(Modifier.height(8.dp))

        if (ordersByUser.isEmpty()) {
            EmptyState(message = "هیچ سفارشی موجود نیست", height = 200.dp)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(ordersByUser.entries.toList()) { (userPhone, userOrders) ->
                    UserCardWithStatus(
                        userPhone = userPhone,
                        userOrders = userOrders,
                        ordersViewModel = ordersViewModel,
                        onClick = {
                            selectedUserOrders = userOrders
                            coroutineScope.launch { bottomSheetState.show() }
                        }
                    )
                }
            }
        }
    }

    if (selectedUserOrders.isNotEmpty()) {
        ModalBottomSheet(
            onDismissRequest = { selectedUserOrders = emptyList() },
            sheetState = bottomSheetState
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "سفارشات کاربر",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                selectedUserOrders.forEach { orderUi ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                "محصول: ${orderUi.productNames}",
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                "تعداد: ${orderUi.order.quantity}",
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                "مبلغ: %,d".format(orderUi.order.totalPrice.toInt()),
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}




