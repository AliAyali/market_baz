package com.aliayali.market_baz.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliayali.market_baz.data.model.ProductCategory
import com.aliayali.market_baz.ui.theme.HoneyGlow
import com.aliayali.market_baz.ui.theme.White

@Composable
fun CategoryItem(
    data: ProductCategory,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) HoneyGlow else White
        ),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = data.displayName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painterResource(data.iconResId),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
