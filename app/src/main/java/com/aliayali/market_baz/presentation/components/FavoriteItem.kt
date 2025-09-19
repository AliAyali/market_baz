package com.aliayali.market_baz.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.aliayali.market_baz.core.utils.calculateDiscountedPrice
import com.aliayali.market_baz.core.utils.formatPrice
import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import com.aliayali.market_baz.ui.theme.CoolSlate
import com.aliayali.market_baz.ui.theme.White

@Composable
fun FavoriteItem(
    favorite: FavoriteEntity?,
    productClicker: () -> Unit,
) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                productClicker()
            },
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                rememberAsyncImagePainter(favorite!!.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.LightGray)
            )

            Text(
                text = favorite.title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (favorite.discount > 0)
                    Text(
                        text = formatPrice(
                            calculateDiscountedPrice(
                                favorite.price,
                                favorite.discount
                            )
                        ),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )
                Text(
                    text = formatPrice(favorite.price),
                    textDecoration = if (favorite.discount > 0) TextDecoration.LineThrough
                    else TextDecoration.None,
                    color = if (favorite.discount > 0) CoolSlate else Color.Black,
                    modifier = Modifier.padding(horizontal = 3.dp)
                )
                Text(
                    text = ":قیمت",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }

        }

    }

}