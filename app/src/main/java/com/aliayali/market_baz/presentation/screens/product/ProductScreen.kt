package com.aliayali.market_baz.presentation.screens.product

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.aliayali.market_baz.R
import com.aliayali.market_baz.core.utils.calculateDiscountedPrice
import com.aliayali.market_baz.core.utils.formatPrice
import com.aliayali.market_baz.data.local.database.entity.CommentEntity
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.CommentItem
import com.aliayali.market_baz.presentation.ui.LineBox
import com.aliayali.market_baz.presentation.ui.QuantitySelector
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.CoolSlate
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.White

@SuppressLint("DefaultLocale")
@Composable
fun ProductScreen(
    navController: NavController,
    productId: String?,
    productViewModel: ProductViewModel = hiltViewModel(),
) {
    val isLoading by productViewModel.isLoading.collectAsState()
    val product by productViewModel.product
    val favorite by productViewModel.isFavorite
    val user = productViewModel.user.value

    LaunchedEffect(productId) {
        productId?.let {
            productViewModel.getProductById(it)
            productViewModel.loadComments(it)
        }
    }

    LaunchedEffect(product) {
        product?.id?.let { productViewModel.checkIfFavorite(it) }
    }

    var quantity by remember { mutableIntStateOf(1) }
    val unitPrice = product?.price ?: 0
    val discountPercent = product?.discount ?: 0
    val clampedDiscount = discountPercent.coerceIn(0, 100)
    val totalPrice = unitPrice * quantity
    val discountedTotal = calculateDiscountedPrice(totalPrice, clampedDiscount)
    var alertDialog by remember { mutableStateOf(false) }
    var alertDialogStar by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }
    val comments by productViewModel.comments.collectAsState(initial = emptyList())
    var rating by remember { mutableIntStateOf(0) }

    if (!isLoading) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
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
                                .clickable { productViewModel.toggleFavorite(product) }
                                .background(IceMist, CircleShape)
                                .padding(9.dp),
                            tint = BrightOrange
                        )
                    else
                        Icon(
                            painterResource(R.drawable.outline_favorite),
                            null,
                            Modifier
                                .clickable { productViewModel.toggleFavorite(product) }
                                .background(IceMist, CircleShape)
                                .padding(9.dp),
                        )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "جزئیات", style = MaterialTheme.typography.titleLarge)
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
            }

            item {
                product?.let {
                    Image(
                        rememberAsyncImagePainter(model = it.imageUrl),
                        null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                Text(
                    text = product?.name ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = product?.description ?: "",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    color = CoolSlate
                )
            }

            item {
                LineBox()
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                        text = ":قیمت",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }

            item {
                LineBox()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = product?.inventory.toString())
                    Text(
                        text = " :موجودی",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }

            item {
                LineBox()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            Icons.Default.Star,
                            null,
                            tint = BrightOrange
                        )
                        Text(text = String.format("%.1f", product?.star ?: 0.0))
                        Button(
                            onClick = {
                                alertDialogStar = true
                            },
                            Modifier.size(height = 35.dp, width = 100.dp)
                        ) {
                            Text(
                                text = "امتیاز بده",
                            )
                        }
                    }
                    Text(
                        text = " :امتیاز",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        fontSize = 10.sp
                    )
                }
            }

            item {
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
            }

            item {
                Button(
                    onClick = { alertDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "افزودن به سبدخرید", fontSize = 20.sp)
                }
            }

            item {
                Text(
                    text = ":نظر کاربران",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    textAlign = TextAlign.End
                )
            }

            item {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    label = {
                        Text(
                            text = "نظر خود را بنویسید",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Right)
                )
                Spacer(Modifier.height(5.dp))
                Button(
                    onClick = {
                        product?.id?.let {
                            productViewModel.insertComment(
                                CommentEntity(
                                    id = 0,
                                    productId = it,
                                    username = user?.name ?: "",
                                    userPhone = user?.phone ?: "",
                                    detail = commentText
                                )
                            )
                        }
                        commentText = ""
                    },
                    enabled = !commentText.isBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CoolSlate)
                ) {
                    Text(text = "ارسال")
                }
            }

            items(comments) { comment ->
                CommentItem(comment, user?.phone ?: "", user?.isAdmin ?: false) {
                    productViewModel.deleteComment(comment)
                }
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }
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
                    ) { Text(text = "بله") }
                    TextButton(onClick = { alertDialog = false }) { Text(text = "خیر") }
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
            shape = RoundedCornerShape(10.dp)
        )

    if (alertDialogStar)
        AlertDialog(
            onDismissRequest = { alertDialogStar = false },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextButton(
                        onClick = {
                            productViewModel.addRating(
                                product, rating
                            )
                            alertDialogStar = false
                        }
                    ) {
                        Text(text = "تایید")
                    }
                    TextButton(onClick = { alertDialogStar = false }) {
                        Text(text = "لغو")
                    }
                }
            },
            title = {
                Text(
                    text = "امتیاز خودتو انتخاب کن",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            text = {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (i in 1..5) {
                        Icon(
                            imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Star $i",
                            tint = if (i <= rating) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { rating = i }
                        )
                        if (i < 5) Spacer(Modifier.width(4.dp))
                    }
                }
            },
            shape = RoundedCornerShape(10.dp)
        )
}
