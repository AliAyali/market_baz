package com.aliayali.market_baz.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aliayali.market_baz.R
import com.aliayali.market_baz.ui.theme.CoolSlate
import com.aliayali.market_baz.ui.theme.MidnightBlue
import com.aliayali.market_baz.ui.theme.White

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    maxQuantity: Int,
) {
    Row(
        modifier = Modifier.background(MidnightBlue, CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
            enabled = quantity > 1
        ) {
            Icon(
                painterResource(R.drawable.ic_min),
                null,
                tint = Color.Black,
                modifier = Modifier
                    .background(CoolSlate, CircleShape)
                    .padding(3.dp)
            )
        }

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = White
        )

        IconButton(
            onClick = { if (quantity < maxQuantity) onQuantityChange(quantity + 1) },
            enabled = quantity < maxQuantity
        ) {
            Icon(
                Icons.Default.Add,
                null,
                modifier = Modifier
                    .background(CoolSlate, CircleShape)
                    .padding(3.dp)
            )
        }
    }
}
