package com.aliayali.market_baz.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.R
import com.aliayali.market_baz.data.model.ProductCategory
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.CategoryItem
import com.aliayali.market_baz.presentation.ui.ProductItemBig
import com.aliayali.market_baz.presentation.ui.ProductItemSmallFavorite
import com.aliayali.market_baz.presentation.ui.SearchTextField
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.MidnightBlue
import com.aliayali.market_baz.ui.theme.SlateGray
import com.aliayali.market_baz.ui.theme.White

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    var selectedCategory by remember { mutableStateOf(ProductCategory.ALL) }
    val categories = ProductCategory.entries
    val user = homeViewModel.user.value
    val filteredProducts = homeViewModel.filteredProducts.value
    val allProduct = homeViewModel.product.value
    val shoppingCardRepositorySize = homeViewModel.shoppingCardRepositorySize.value

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
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
                            .padding(15.dp)
                            .size(20.dp)
                            .clickable {
                                navController.navigate(NavigationScreen.ShoppingCart.route) {
                                    popUpTo(NavigationScreen.Home.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                    )
                    if (shoppingCardRepositorySize > 0)
                        Text(
                            text = shoppingCardRepositorySize.toString(),
                            color = White,
                            modifier = Modifier
                                .background(
                                    BrightOrange,
                                    CircleShape
                                )
                                .padding(horizontal = 7.dp),
                            fontWeight = FontWeight.Bold
                        )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "تحویل به",
                        color = BrightOrange,
                        fontSize = 18.sp
                    )
                    Text(
                        text = user?.address ?: "null",
                        color = SlateGray,
                        fontSize = 18.sp
                    )
                }

                Image(
                    painterResource(R.drawable.menu),
                    null,
                    Modifier
                        .background(IceMist, CircleShape)
                        .padding(15.dp)
                        .size(20.dp)
                        .clickable {
                            navController.navigate(NavigationScreen.Profile.route) {
                                popUpTo(NavigationScreen.Home.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                )
            }
            Spacer(Modifier.height(30.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "سلام ${user?.name ?: "کاربر"}، روزت بخیر!",
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        textDirection = TextDirection.Rtl,
                    )
                )
            }
            Spacer(Modifier.height(30.dp))
        }

        item {
            SearchTextField(
                ""
            ) {}
            Spacer(Modifier.height(30.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        null
                    )
                    Text(
                        text = "دیدن همه",
                        fontSize = 18.sp
                    )
                }
                Text(
                    text = "محبوب",
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.height(10.dp))
        }

        item {
            LazyRow(
                reverseLayout = true
            ) {
                items(allProduct) {
                    if ((it?.discount ?: 0) > 0)
                        ProductItemSmallFavorite(it) {
                            navController.navigate(
                                NavigationScreen.Product.createRoute(it?.id ?: 0)
                            )
                        }
                }
            }
            Spacer(Modifier.height(30.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        null
                    )
                    Text(
                        text = "دیدن همه",
                        fontSize = 18.sp
                    )
                }
                Text(
                    text = "همه دسته ها",
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.height(10.dp))
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                reverseLayout = true,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(categories) { category ->
                    CategoryItem(
                        data = category,
                        selected = category == selectedCategory
                    ) {
                        homeViewModel.getCategory(category.id)
                        selectedCategory = category
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
        }

        items(
            if (selectedCategory.id == 0) allProduct else filteredProducts
        ) { product ->
            ProductItemBig(product) {
                navController.navigate(
                    NavigationScreen.Product.createRoute(product?.id ?: 0)
                )
            }
        }
    }
}