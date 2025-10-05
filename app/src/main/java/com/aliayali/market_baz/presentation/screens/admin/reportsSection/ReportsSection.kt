package com.aliayali.market_baz.presentation.screens.admin.reportsSection

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.presentation.screens.admin.components.ReportCard

@Composable
fun ReportsSection(
    navController: NavController,
    reportsViewModel: ReportsViewModel = hiltViewModel(),
) {
    val userCount = reportsViewModel.userCount.collectAsState()
    val productCount = reportsViewModel.productCount.collectAsState()
    val commentCount = reportsViewModel.commentCount.collectAsState()
    val isLoading = reportsViewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        reportsViewModel.loadReports()
    }

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
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    Icons.Default.Home,
                    "Home",
                    Modifier.size(28.dp)
                )
            }
            Text(
                text = "گزارشات",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                ReportCard(
                    title = "تعداد کاربران",
                    value = userCount.value.toString(),
                    isLoading.value
                )
            }
            item {
                ReportCard(
                    title = "تعداد کالاها",
                    value = productCount.value.toString(),
                    isLoading.value
                )
            }
            item {
                ReportCard(
                    title = "تعداد نظرها",
                    value = commentCount.value.toString(),
                    isLoading.value
                )
            }
        }
    }
}
