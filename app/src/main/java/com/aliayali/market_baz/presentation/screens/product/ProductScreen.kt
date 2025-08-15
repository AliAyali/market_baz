package com.aliayali.market_baz.presentation.screens.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.R
import com.aliayali.market_baz.core.utils.calculateDiscountedPrice
import com.aliayali.market_baz.core.utils.formatPrice
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.CommentItem
import com.aliayali.market_baz.presentation.ui.LineBox
import com.aliayali.market_baz.presentation.ui.QuantitySelector
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.CoolSlate
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.MidnightBlue
import com.aliayali.market_baz.ui.theme.White

@Composable
fun ProductScreen(
    navController: NavController,
    productId: Int?,
    productViewModel: ProductViewModel = hiltViewModel(),
) {
    val product by productViewModel.product
    val favorite by productViewModel.isFavorite
    LaunchedEffect(productId) {
        productViewModel.getProductById(productId)
    }
    LaunchedEffect(product) {
        product?.let {
            productViewModel.checkIfFavorite(it.id)
        }
    }
    var quantity by remember { mutableIntStateOf(1) }
    val unitPrice = product?.price ?: 0
    val discountPercent = product?.discount ?: 0
    val clampedDiscount = discountPercent.coerceIn(0, 100)
    val totalPrice = unitPrice * quantity
    val discountedTotal = calculateDiscountedPrice(totalPrice, clampedDiscount)
    var alertDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (favorite)
                Icon(
                    Icons.Outlined.Favorite,
                    null,
                    Modifier
                        .clickable {
                            productViewModel.toggleFavorite(product)
                        }
                        .background(IceMist, CircleShape)
                        .padding(9.dp),
                    tint = BrightOrange
                )
            else
                Icon(
                    painterResource(R.drawable.outline_favorite),
                    null,
                    Modifier
                        .clickable {
                            productViewModel.toggleFavorite(product)
                        }
                        .background(IceMist, CircleShape)
                        .padding(9.dp),
                )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "جزئیات",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.width(20.dp))
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    null,
                    modifier = Modifier
                        .background(IceMist, CircleShape)
                        .padding(9.dp)
                        .clickable {
                            navController.navigate(NavigationScreen.Home.route) {
                                popUpTo(NavigationScreen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        product?.let {
            Image(
                painterResource(product?.imageUrl ?: 0),
                null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(30.dp))

        Text(
            text = product?.name ?: "name",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = product?.description ?: "description",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            color = CoolSlate
        )

        LineBox()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (clampedDiscount > 0) {
                Text(
                    text = formatPrice(discountedTotal),
                    fontSize = 18.sp
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = formatPrice(totalPrice),
                    textDecoration = TextDecoration.LineThrough,
                    color = CoolSlate
                )
            } else {
                Text(
                    text = formatPrice(totalPrice),
                    color = Color.Black
                )
            }
            if (clampedDiscount > 0)
                Text(
                    text = "$clampedDiscount%",
                    color = White,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(
                            BrightOrange,
                            CircleShape
                        )
                        .padding(vertical = 3.dp, horizontal = 5.dp)
                )
            Text(
                text = ":قیمت",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        LineBox()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = product?.inventory.toString(),
            )
            Text(
                text = " :موجودی",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        LineBox()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                Icons.Outlined.Star,
                null,
                tint = BrightOrange
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = product?.star.toString(),
            )
            Text(
                text = ":امتیاز",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        LineBox()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuantitySelector(
                quantity = quantity,
                onQuantityChange = { quantity = it },
                maxQuantity = product?.inventory ?: Int.MAX_VALUE
            )
            Text(text = ":تعداد")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                alertDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "افزودن به سبدخرید",
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.height(30.dp))

        Text(
            text = ":نظر کاربران",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        Spacer(Modifier.height(10.dp))

        repeat(10) {
            CommentItem()
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
                                productViewModel.insertShoppingCard(
                                    product = product,
                                    price = discountedTotal,
                                    number = quantity
                                )
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
                        text = "سبد خرید",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
                text = {
                    Text(
                        text = "به سبد خرید اضافه شود؟",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
                icon = {
                    Icon(
                        painterResource(R.drawable.shopping),
                        null,
                        tint = MidnightBlue,
                        modifier = Modifier.size(20.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp)
            )

    }
}