package com.aliayali.market_baz.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.White

@Composable
fun ProductItemSmallFavorite(
    data: ProductEntity?,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .size(120.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                rememberAsyncImagePainter(data?.imageUrl ?: 0),
                null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            BrightOrange,
                            CircleShape
                        )
                        .padding(vertical = 2.dp, horizontal = 5.dp)
                ) {
                    Text(
                        text = "%",
                        fontSize = 15.sp,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = data?.discount.toString(),
                        fontSize = 15.sp,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }

}