package com.aliayali.market_baz.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliayali.market_baz.R
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.presentation.ui.ProductItemSmallFavorite
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.MidnightBlue
import com.aliayali.market_baz.ui.theme.SlateGray
import com.aliayali.market_baz.ui.theme.White

@Composable
fun HomeHeader(
    user: UserEntity,
    shoppingCartSize: Int,
    onCartClick: () -> Unit,
    onMenuClick: () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Image(
                painterResource(R.drawable.shopping),
                null,
                Modifier
                    .background(MidnightBlue, CircleShape)
                    .size(50.dp)
                    .clickable { onCartClick() }
                    .padding(12.dp)
            )
            if (shoppingCartSize > 0) {
                Text(
                    text = shoppingCartSize.toString(),
                    color = White,
                    modifier = Modifier
                        .background(BrightOrange, CircleShape)
                        .align(Alignment.TopEnd)
                        .padding(horizontal = 7.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.width(200.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "تحویل به",
                color = BrightOrange,
                fontSize = 16.sp
            )
            Text(
                text = user.address,
                color = SlateGray,
                fontSize = 14.sp,
                maxLines = 2,
                textAlign = TextAlign.End
            )
        }

        Image(
            painterResource(R.drawable.menu),
            null,
            Modifier
                .background(IceMist, CircleShape)
                .size(50.dp)
                .clickable { onMenuClick() }
                .padding(12.dp)
        )
    }
}

@Composable
fun GreetingSection(userName: String?) {
    Text(
        text = "سلام ${userName ?: "کاربر"}، روزت بخیر!",
        fontSize = 18.sp,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(textDirection = TextDirection.Rtl)
    )
}

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null)
            Text(
                text = "دیدن همه",
                fontSize = 16.sp,
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun ProductListRow(
    products: List<ProductEntity>,
    emptyMessage: String,
    onProductClick: (ProductEntity) -> Unit,
) {
    if (products.isEmpty()) {
        com.aliayali.market_baz.presentation.components.EmptyState(
            message = emptyMessage,
            height = 150.dp
        )
    } else {
        androidx.compose.foundation.lazy.LazyRow(
            Modifier.fillMaxWidth(),
            reverseLayout = true,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductItemSmallFavorite(product) {
                    onProductClick(product)
                }
            }
        }
    }
}

@Composable
fun AdminFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = BrightOrange,
        modifier = modifier
    ) {
        Text(
            text = "ادمین",
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}