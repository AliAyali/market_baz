package com.aliayali.market_baz.presentation.screens.admin.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.aliayali.market_baz.core.utils.calculateDiscountedPrice
import com.aliayali.market_baz.core.utils.formatPrice
import com.aliayali.market_baz.domain.model.Product
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.CoolSlate
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.White

@Composable
fun ProductsItem(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onEdit: () -> Unit,
) {
    var quantity by remember { mutableIntStateOf(1) }
    val unitPrice = product.price
    val discountPercent = product.discount
    val clampedDiscount = discountPercent.coerceIn(0, 100)
    val totalPrice = unitPrice * quantity
    val discountedTotal = calculateDiscountedPrice(totalPrice, clampedDiscount)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = IceMist
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Edit,
                        "edit",
                        Modifier.clickable {
                            onEdit()
                        },
                        BrightOrange
                    )
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "موجودی: ${product.inventory}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (clampedDiscount > 0) {
                            Text(text = formatPrice(discountedTotal), fontSize = 18.sp)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = formatPrice(totalPrice),
                                textDecoration = TextDecoration.LineThrough,
                                color = CoolSlate
                            )
                        } else {
                            Text(text = formatPrice(totalPrice), color = Color.Black)
                        }
                        if (clampedDiscount > 0)
                            Text(
                                text = "$clampedDiscount%",
                                color = White,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .background(BrightOrange, CircleShape)
                                    .padding(vertical = 3.dp, horizontal = 5.dp)
                            )
                        Text(
                            text = " : قیمت"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    if (product.numberOfComments > 0) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "(${product.numberOfComments} نظر)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    RatingStars(rating = product.star, starSize = 16.dp, showNumber = false)
                }
            }
            Image(
                painter = rememberAsyncImagePainter(model = product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(start = 12.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
        }
    }
}
