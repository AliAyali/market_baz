package com.aliayali.market_baz.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliayali.market_baz.core.utils.calculateDiscountedPrice
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.CoolSlate
import com.aliayali.market_baz.ui.theme.White

@Composable
fun ProductItemBig(
    data: ProductEntity?,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp), colors = CardDefaults.cardColors(
            containerColor = White
        ), elevation = CardDefaults.cardElevation(5.dp)
    ) {

        Image(
            painterResource(data?.imageUrl ?: 0),
            null,
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 50.dp)
                .clip(RoundedCornerShape(10.dp))
                .height(150.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = data?.name ?: "",
            textAlign = TextAlign.End
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = "موجودی: ${data?.inventory}",
            textAlign = TextAlign.End
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Icon(
                    Icons.Default.Star, null, tint = BrightOrange, modifier = Modifier.size(20.dp)
                )
                Text(
                    text = data?.star.toString(), fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                if ((data?.discount ?: -1) > 0) Text(
                    text = calculateDiscountedPrice(
                        data?.price ?: 0, data?.discount ?: 0
                    ).toString(), fontSize = 18.sp
                )
                Text(
                    text = data?.price.toString(),
                    textDecoration = if ((data?.discount ?: -1) > 0) TextDecoration.LineThrough
                    else TextDecoration.None,
                    color = if ((data?.discount ?: -1) > 0) CoolSlate else Color.Black,
                )
                Text(
                    text = ":قیمت "
                )
            }
        }
    }
}