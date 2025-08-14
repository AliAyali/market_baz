package com.aliayali.market_baz.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.White

@Composable
fun ProfileItem(
    icon: Int,
    title: String,
    color: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                IceMist,
                RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            null
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title
            )
            Spacer(Modifier.width(10.dp))
            Icon(
                painterResource(icon),
                null,
                modifier = Modifier
                    .background(White, CircleShape)
                    .padding(10.dp)
                    .size(18.dp),
                tint = color
            )
        }
    }
}