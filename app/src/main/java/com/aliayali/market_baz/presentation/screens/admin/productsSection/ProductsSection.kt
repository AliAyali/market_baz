package com.aliayali.market_baz.presentation.screens.admin.productsSection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.screens.admin.components.ProductsItem
import com.aliayali.market_baz.presentation.ui.SearchTextField

@Composable
fun ProductsSection(
    navController: NavController,
    productsViewModel: ProductsViewModel = hiltViewModel(),
) {
    var searchQuery by remember { mutableStateOf("") }
    val products = productsViewModel.displayedProducts.value

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.navigate(NavigationScreen.Home.route) {
                        popUpTo(NavigationScreen.Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    Modifier.size(28.dp)
                )
            }
            Text(
                text = "مدیریت محصولات",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.height(10.dp))


        SearchTextField(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                productsViewModel.searchProducts(it)
            }
        )

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                navController.navigate(NavigationScreen.AddProduct.createRoute(0)) {
                    popUpTo(NavigationScreen.AddProduct.route) { inclusive = false }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "افزودن محصول جدید",
                fontSize = 18.sp
            )
        }

        Spacer(Modifier.height(10.dp))

        LazyColumn {
            items(products) { product ->
                ProductsItem(
                    product = product,
                    onClick = {
                        navController.navigate(
                            NavigationScreen.Product.createRoute(product.id)
                        )
                    },
                    onEdit = {
                        navController.navigate(NavigationScreen.AddProduct.createRoute(product.id)) {
                            popUpTo(NavigationScreen.AddProduct.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}