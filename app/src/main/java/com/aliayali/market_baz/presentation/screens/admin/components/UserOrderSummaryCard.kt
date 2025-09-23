package com.aliayali.market_baz.presentation.screens.admin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun UserOrderSummaryCard(
    products: String,
    address: String,
    quantity: Int,
    price: Int,
    status: String,
) {
    val statusMap = mapOf(
        "PENDING" to "در حال انتظار",
        "PAID" to "پرداخت شده",
        "SHIPPED" to "در حال ارسال",
        "DELIVERED" to "تحویل داده شده"
    )

    val statusStepMap = mapOf(
        "PENDING" to 1f,
        "PAID" to 2f,
        "SHIPPED" to 3f,
        "DELIVERED" to 4f
    )

    val normalizedStatus = when (status) {
        "PENDING", "PAID", "SHIPPED", "DELIVERED" -> status
        "در حال انتظار" -> "PENDING"
        "پرداخت شده" -> "PAID"
        "در حال ارسال" -> "SHIPPED"
        "تحویل داده شده" -> "DELIVERED"
        else -> "PENDING"
    }

    val displayStatus = statusMap[normalizedStatus] ?: status
    val step = statusStepMap[normalizedStatus] ?: 1f
    val progress = step / 4f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {
        Column(Modifier.padding(24.dp)) {
            Text(
                "محصولات: $products",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))

            Text(
                "آدرس تحویل: $address",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "تعداد کل: $quantity",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    "مبلغ کل: %,d تومان".format(price),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "وضعیت سفارش: $displayStatus",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp)
                    .clip(RoundedCornerShape(7.dp)),
                color = when (normalizedStatus) {
                    "PENDING" -> Color(0xFFD81B60)
                    "PAID" -> Color(0xFF2196F3)
                    "SHIPPED" -> Color(0xFFFFC107)
                    "DELIVERED" -> Color(0xFF4CAF50)
                    else -> Color(0xFF4CAF50)
                },
                trackColor = Color(0xFFF0F0F0)
            )

        }
    }
}
