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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.data.model.ProductCategory
import com.aliayali.market_baz.domain.model.Product
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.screens.admin.components.GalleryImagePickerProduct
import com.aliayali.market_baz.presentation.screens.admin.components.saveImageToInternalStorage
import com.aliayali.market_baz.ui.theme.IceMist
import java.io.File

@Composable
fun AddProductScreen(
    navController: NavController,
    productId: String?,
    addProductViewModel: AddProductViewModel = hiltViewModel(),
) {
    val isLoadingAdd by addProductViewModel.isLoadingAdd.collectAsState()
    val isLoadingDelete by addProductViewModel.isLoadingDelete.collectAsState()
    val isLoadingScreen by addProductViewModel.isLoadingScreen.collectAsState()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var inventory by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(ProductCategory.ALL) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var currentProduct by remember { mutableStateOf<Product?>(null) }

    if (productId != null && currentProduct == null) {
        LaunchedEffect(productId) {
            addProductViewModel.getProductById(productId) { product ->
                product?.let {
                    currentProduct = it
                    name = it.name
                    description = it.description ?: ""
                    price = it.price.toString()
                    discount = it.discount.toString()
                    inventory = it.inventory.toString()
                    selectedCategory =
                        ProductCategory.entries.firstOrNull { cat -> cat.id == it.categoryId }
                            ?: ProductCategory.ALL
                    selectedImageUri =
                        if (it.imageUrl.isNotEmpty()) Uri.fromFile(File(it.imageUrl)) else null
                }
            }
        }
    }

    if (isLoadingScreen)
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


    if (!isLoadingScreen)
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
                    contentDescription = null,
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
                    text = if (currentProduct == null) "افزودن محصول جدید" else "ویرایش محصول",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            GalleryImagePickerProduct(
                selectedImageUri = selectedImageUri,
                onImageSelected = { selectedImageUri = it }
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("نام محصول", textAlign = TextAlign.End) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("توضیحات", textAlign = TextAlign.End) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("قیمت", textAlign = TextAlign.End) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = discount,
                    onValueChange = { discount = it },
                    label = { Text("تخفیف (%)", textAlign = TextAlign.End) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = inventory,
                onValueChange = { inventory = it },
                label = { Text("موجودی", textAlign = TextAlign.End) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
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
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        Text(text = selectedCategory.displayName)
                    }
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    ProductCategory.entries.forEach { category ->
                        DropdownMenuItem(onClick = {
                            selectedCategory = category
                            expanded = false
                        }, text = {
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
                        })
                    }
                }
            }

            Button(
                onClick = {
                    val imagePath = selectedImageUri?.let { uri ->
                        saveImageToInternalStorage(navController.context, uri)
                    }
                    val product = Product(
                        id = currentProduct?.id,
                        name = name,
                        description = description,
                        price = price.toIntOrNull() ?: 0,
                        discount = discount.toIntOrNull() ?: 0,
                        inventory = inventory.toIntOrNull() ?: 0,
                        categoryId = selectedCategory.id,
                        imageUrl = imagePath ?: ""
                    )

                    if (currentProduct == null) {
                        addProductViewModel.addProduct(
                            product,
                            onSuccess = {
                                navController.navigate(NavigationScreen.Admin.route) {
                                    popUpTo(NavigationScreen.Admin.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            },
                            onError = {}
                        )
                    } else {
                        addProductViewModel.updateProduct(
                            product,
                            onSuccess = {
                                navController.navigate(NavigationScreen.Admin.route) {
                                    popUpTo(NavigationScreen.Admin.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            },
                            onError = {}
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                enabled = !isLoadingAdd
            ) {
                if (isLoadingAdd) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "ذخیره محصول", fontSize = 18.sp)
                }
            }

            if (currentProduct != null) {
                var showDialog by remember { mutableStateOf(false) }
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !isLoadingDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    if (isLoadingDelete)
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    else
                        Text(text = "حذف محصول", color = MaterialTheme.colorScheme.onError)
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = {
                            Text(
                                "حذف محصول",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        },
                        text = {
                            Text(
                                "آیا مطمئن هستید می‌خواهید این محصول را حذف کنید؟",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                currentProduct?.let {
                                    addProductViewModel.deleteProduct(
                                        it,
                                        onSuccess = {
                                            navController.navigate(NavigationScreen.Admin.route) {
                                                popUpTo(NavigationScreen.Admin.route) {
                                                    inclusive = true
                                                }
                                                launchSingleTop = true
                                            }
                                        },
                                        onError = { }
                                    )
                                }
                                showDialog = false
                            }) { Text("بله") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) { Text("خیر") }
                        }
                    )
                }
            }
        }
}