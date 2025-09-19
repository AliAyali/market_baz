package com.aliayali.market_baz.presentation.screens.admin.addProduct

import android.net.Uri
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.model.ProductCategory
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.screens.admin.components.GalleryImagePicker
import com.aliayali.market_baz.presentation.screens.admin.components.saveImageToInternalStorage
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun AddProductScreen(
    navController: NavController,
    addProductViewModel: AddProductViewModel = hiltViewModel(),
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var inventory by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(ProductCategory.ALL) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                null,
                modifier = Modifier
                    .background(IceMist, CircleShape)
                    .padding(9.dp)
                    .clickable {
                        navController.navigate(NavigationScreen.Admin.route) {
                            popUpTo(NavigationScreen.Admin.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
            )
            Text(
                text = "افزودن محصول جدید",
                style = MaterialTheme.typography.titleLarge
            )
        }

        GalleryImagePicker(
            selectedImageUri = selectedImageUri,
            onImageSelected = { selectedImageUri = it }
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = "نام محصول",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End
            )
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = {
                Text(
                    text = "توضیحات",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = {
                    Text(
                        text = "قیمت",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End
                ),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = discount,
                onValueChange = { discount = it },
                label = {
                    Text(
                        text = "تخفیف (%)",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End
                ),
                modifier = Modifier.weight(1f)
            )
        }


        OutlinedTextField(
            value = inventory,
            onValueChange = { inventory = it },
            label = {
                Text(
                    text = "موجودی",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End
            )
        )

        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        null
                    )
                    Text(
                        text = selectedCategory.displayName
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                ProductCategory.entries.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        },
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = category.iconResId),
                                    contentDescription = category.displayName,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = category.displayName,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                val imagePath = selectedImageUri?.let { uri ->
                    saveImageToInternalStorage(navController.context, uri)
                }
                val product = ProductEntity(
                    name = name,
                    description = description,
                    price = price.toIntOrNull() ?: 0,
                    discount = discount.toIntOrNull() ?: 0,
                    inventory = inventory.toIntOrNull() ?: 0,
                    categoryId = selectedCategory.id,
                    imageUrl = imagePath!!
                )
                addProductViewModel.addProduct(product) {
                    navController.navigate(NavigationScreen.Admin.route) {
                        popUpTo(NavigationScreen.Admin.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "ذخیره محصول",
                fontSize = 18.sp
            )
        }
    }
}