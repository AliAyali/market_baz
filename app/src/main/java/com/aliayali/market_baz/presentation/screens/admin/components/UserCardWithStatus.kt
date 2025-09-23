package com.aliayali.market_baz.presentation.screens.admin.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aliayali.market_baz.data.model.OrderStatus
import com.aliayali.market_baz.presentation.screens.admin.ordersSection.OrdersViewModel

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

            val statuses = listOf(
                OrderStatus.PENDING,
                OrderStatus.PAID,
                OrderStatus.SHIPPED,
                OrderStatus.DELIVERED
            )
            val statusLabels = mapOf(
                OrderStatus.PENDING to "در حال انتظار",
                OrderStatus.PAID to "پرداخت شده",
                OrderStatus.SHIPPED to "ارسال شده",
                OrderStatus.DELIVERED to "تحویل داده شده"
            )
            val statusColors = mapOf(
                OrderStatus.PENDING to Color(0xFFE53935),
                OrderStatus.PAID to Color(0xFF2196F3),
                OrderStatus.SHIPPED to Color(0xFFFF9800),
                OrderStatus.DELIVERED to Color(0xFF4CAF50)
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
                                Text(text = statusLabels[status] ?: status, textAlign = TextAlign.Center)
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