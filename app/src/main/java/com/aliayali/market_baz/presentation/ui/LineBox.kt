package com.aliayali.market_baz.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aliayali.market_baz.ui.theme.MidnightBlue

@Composable
fun LineBox() {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 16.dp)
            .height(1.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color.Transparent,
                        MidnightBlue.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                )
            )
    )
}