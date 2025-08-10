package com.aliayali.market_baz.presentation.screen.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.navigation.NavigationScreen

@Composable
fun AdminScreen(
    navController: NavController,
    adminViewModel: AdminViewModel = hiltViewModel(),
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("") }
    var inventory by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("عنوان") }
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text("متن") }
        )
        TextField(
            value = price,
            onValueChange = { price = it },
            placeholder = { Text("قیمت") }
        )
        TextField(
            value = discount,
            onValueChange = { discount = it },
            placeholder = { Text("تخفیف") }
        )
        TextField(
            value = categoryId,
            onValueChange = { categoryId = it },
            placeholder = { Text("دسته بندی") }
        )
        TextField(
            value = inventory,
            onValueChange = { inventory = it },
            placeholder = { Text("موجودی") }
        )

        Button(
            onClick = {
                adminViewModel.insertProduct(
                    name,
                    description,
                    price.toInt(),
                    discount.toInt(),
                    categoryId.toInt(),
                    inventory.toInt()
                )
            }
        ) {
            Text("ذخیره")
        }
        Button(
            onClick = {
                navController.navigate(NavigationScreen.Home.route)
            }
        ) {
            Text("رفتن به خانه")
        }
    }

}