package com.aliayali.market_baz.presentation.screens.admin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun RatingStars(
    rating: Double,
    starSize: Dp = 16.dp,
    showNumber: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val rounded = (rating * 2).roundToInt() / 2.0
    val fullStars = rounded.toInt()
    val hasHalf = (rounded - fullStars) == 0.5
    val emptyStars = 5 - fullStars - if (hasHalf) 1 else 0

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFA000),
                modifier = Modifier.size(starSize)
            )
        }

        if (hasHalf) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFA000),
                modifier = Modifier.size(starSize)
            )
        }

        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFA000).copy(alpha = 0.4f),
                modifier = Modifier.size(starSize)
            )
        }

        if (showNumber) {
            Spacer(Modifier.width(6.dp))
            Text(text = "%.1f".format(rating))
        }
    }
}
