package com.aliayali.market_baz.presentation.screens.admin.ordersSection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun OrdersSection(
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

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(ordersByUser.entries.toList()) { (userPhone, userOrders) ->
                UserCardWithStatus(
                    userPhone = userPhone,
                    userOrders = userOrders,
                    ordersViewModel = ordersViewModel,
                    onClick = {
                        selectedUserOrders = userOrders
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    }
                )
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


@Composable
fun UserCardWithStatus(
    userPhone: String,
    userOrders: List<OrdersViewModel.OrderUiState>,
    ordersViewModel: OrdersViewModel,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "کاربر: $userPhone",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "آدرس: ${userOrders.firstOrNull()?.userAddress ?: ""}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            val statuses = listOf("در حال انتظار", "پرداخت شده", "ارسال شده", "تحویل داده شده")

            val statusColors = mapOf(
                "در حال انتظار" to Color(0xFFE53935),
                "پرداخت شده" to Color(0xFF2196F3),
                "ارسال شده" to Color(0xFFFF9800),
                "تحویل داده شده" to Color(0xFF4CAF50)
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                statuses.chunked(2).forEach { rowStatuses ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowStatuses.forEach { status ->
                            val isSelected = userOrders.all { it.order.status == status }
                            val baseColor = statusColors[status] ?: Color.Gray

                            Button(
                                onClick = {
                                    userOrders.forEach { orderUi ->
                                        ordersViewModel.updateOrderStatus(orderUi.order, status)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) baseColor else Color(0xFFBDBDBD),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = status, textAlign = TextAlign.Center)
                            }
                        }
                        if (rowStatuses.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }


        }
    }
}


