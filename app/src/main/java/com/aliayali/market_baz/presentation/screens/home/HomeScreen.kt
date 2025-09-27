package com.aliayali.market_baz.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.data.model.ProductCategory
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.CategoryItem
import com.aliayali.market_baz.presentation.components.EmptyState
import com.aliayali.market_baz.presentation.ui.ProductItemBig
import com.aliayali.market_baz.presentation.ui.SearchTextField

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by homeViewModel.uiState.collectAsState()
    var selectedCategory by remember { mutableStateOf(ProductCategory.ALL) }
    var searchQuery by remember { mutableStateOf("") }

    val displayedProducts = when {
        searchQuery.isNotEmpty() -> uiState.searchResults
        selectedCategory.id == 0 -> uiState.products
        else -> uiState.filteredProducts
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                uiState.user?.let {
                    HomeHeader(
                        user = it,
                        shoppingCartSize = uiState.shoppingCartSize,
                        onCartClick = { navController.navigate(NavigationScreen.ShoppingCart.route) },
                        onMenuClick = { navController.navigate(NavigationScreen.Profile.route) }
                    )
                }
            }

            item {
                GreetingSection(userName = uiState.user?.name)
            }

            item {
                SearchTextField(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                        homeViewModel.searchProducts(it)
                    }
                )
            }

            item {
                SectionHeader(
                    title = "محبوب",
                    onSeeAllClick = {
                        navController.navigate(NavigationScreen.SeeAll.createRoute("محبوب ها"))
                    }
                )
            }

            item {
                ProductListRow(
                    products = uiState.products.filter { it.discount > 0 },
                    emptyMessage = "هیچ محصول محبوبی موجود نیست",
                    onProductClick = { product ->
                        navController.navigate(NavigationScreen.Product.createRoute(product.id))
                    }
                )
            }

            item {
                SectionHeader(
                    title = "همه دسته‌ها",
                    onSeeAllClick = {
                        navController.navigate(NavigationScreen.SeeAll.createRoute("همه محصولات"))
                    }
                )
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    reverseLayout = true
                ) {
                    items(ProductCategory.entries) { category ->
                        CategoryItem(
                            data = category,
                            selected = category == selectedCategory
                        ) {
                            homeViewModel.selectCategory(category.id)
                            selectedCategory = category
                        }
                    }
                }
            }

            if (displayedProducts.isEmpty()) {
                item {
                    EmptyState(message = "هیچ محصولی موجود نیست", height = 200.dp)
                }
            } else {
                items(displayedProducts) { product ->
                    ProductItemBig(product) {
                        navController.navigate(NavigationScreen.Product.createRoute(product.id))
                    }
                }
            }
        }

        if (uiState.user?.isAdmin == true) {
            AdminFab(
                onClick = { navController.navigate(NavigationScreen.Admin.route) },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}
