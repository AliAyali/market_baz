package com.aliayali.market_baz.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.aliayali.market_baz.R
import com.aliayali.market_baz.core.utils.formatPrice
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.ui.theme.SoftGray
import com.aliayali.market_baz.ui.theme.White

@Composable
fun ShoppingCardItem(
    shoppingCard: ShoppingCardEntity,
    onIncrease: (ShoppingCardEntity) -> Unit,
    onDecrease: (ShoppingCardEntity) -> Unit,
    onDelete: (ShoppingCardEntity) -> Unit,
) {
    val number = shoppingCard.number
    val price = shoppingCard.price
    var alertDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(
                SoftGray.copy(alpha = 0.1f),
                RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = {
                alertDialog = true
            }
        ) {
            Icon(
                Icons.Default.Clear,
                null,
                tint = White,
                modifier = Modifier
                    .background(Color(0XFFE04444), CircleShape)
                    .padding(3.dp)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = shoppingCard.name, color = White)
            Text(text = formatPrice((price * number)), color = White)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onDecrease(shoppingCard) }) {
                    Icon(
                        painterResource(R.drawable.ic_min),
                        null,
                        modifier = Modifier
                            .background(White.copy(alpha = 0.2f), CircleShape)
                            .padding(3.dp),
                        tint = White
                    )
                }
                Text(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    text = number.toString(),
                    color = White
                )
                IconButton(onClick = { onIncrease(shoppingCard) }) {
                    Icon(
                        Icons.Default.Add,
                        null,
                        modifier = Modifier
                            .background(White.copy(alpha = 0.2f), CircleShape)
                            .padding(3.dp),
                        tint = White
                    )
                }
            }
        }

        Image(
            rememberAsyncImagePainter(shoppingCard.imageUrl),
            null,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(100.dp)
        )
    }

    if (alertDialog)
        AlertDialog(
            onDismissRequest = { alertDialog = false },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextButton(
                        onClick = {
                            onDelete(shoppingCard)
                            alertDialog = false
                        }
                    ) {
                        Text(
                            text = "بله"
                        )
                    }
                    TextButton(
                        onClick = {
                            alertDialog = false
                        }
                    ) {
                        Text(
                            text = "خیر"
                        )
                    }
                }
            },
            title = {
                Text(
                    text = "حذف",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            text = {
                Text(
                    text = "کالا از سبد خرید حذف شود؟",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            icon = {
                Icon(
                    Icons.Default.Close,
                    null,
                    tint = Color(0xFFE04444),
                    modifier = Modifier.size(20.dp)
                )
            },
            shape = RoundedCornerShape(10.dp)
        )
}
