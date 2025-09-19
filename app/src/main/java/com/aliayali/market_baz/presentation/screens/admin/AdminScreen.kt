package com.aliayali.market_baz.presentation.screens.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aliayali.market_baz.R
import com.aliayali.market_baz.presentation.screens.admin.ordersSection.OrdersSection
import com.aliayali.market_baz.presentation.screens.admin.productsSection.ProductsSection
import com.aliayali.market_baz.presentation.screens.admin.reportsSection.ReportsSection
import com.aliayali.market_baz.presentation.screens.admin.usersSection.UsersSection

@Composable
fun AdminScreen(
    navController: NavController
) {
    var selectedTab by rememberSaveable { mutableStateOf(AdminTab.Products) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == AdminTab.Products,
                    onClick = { selectedTab = AdminTab.Products },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.product_admin),
                            contentDescription = "Products",
                            Modifier.size(28.dp)
                        )
                    },
                    label = { Text("محصولات") }
                )
                NavigationBarItem(
                    selected = selectedTab == AdminTab.Orders,
                    onClick = { selectedTab = AdminTab.Orders },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.order_admin),
                            contentDescription = "Orders",
                            Modifier.size(28.dp)
                        )
                    },
                    label = { Text("سفارش‌ها") }
                )
                NavigationBarItem(
                    selected = selectedTab == AdminTab.Users,
                    onClick = { selectedTab = AdminTab.Users },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.user_admin),
                            contentDescription = "Users",
                            Modifier.size(28.dp)
                        )
                    },
                    label = { Text("کاربران") }
                )
                NavigationBarItem(
                    selected = selectedTab == AdminTab.Reports,
                    onClick = { selectedTab = AdminTab.Reports },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.report_admin),
                            contentDescription = "Reports",
                            Modifier.size(28.dp)
                        )
                    },
                    label = { Text("گزارشات") }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                AdminTab.Products -> ProductsSection(navController)
                AdminTab.Orders -> OrdersSection(navController)
                AdminTab.Users -> UsersSection(navController)
                AdminTab.Reports -> ReportsSection(navController)
            }
        }
    }
}
