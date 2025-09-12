package com.aliayali.market_baz.presentation.screens.addNewAddress

import CustomTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.data.local.database.entity.AddressEntity
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun AddNewAddressScreen(
    navController: NavController,
    addressId: Int,
    addNewAddressViewModel: AddNewAddressViewModel = hiltViewModel(),
) {
    addNewAddressViewModel.getAddressById(addressId)
    val address = addNewAddressViewModel.address.value

    var nameAddress by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("مشهد") }
    var street by remember { mutableStateOf("") }
    var milan by remember { mutableStateOf("") }
    var plate by remember { mutableStateOf("") }
    var floor by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(address) {
        address?.let {
            nameAddress = it.name
            city = it.city
            street = it.street
            milan = it.milan
            plate = it.plate
            floor = it.floor
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                        navController.navigate(NavigationScreen.Address.route) {
                            popUpTo(NavigationScreen.Address.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
            )
            Text(
                text = if (addressId == 0) "اضافه کردن آدرس جدید" else "ویرایش آدرس",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Column {
            CustomTextField(
                value = nameAddress,
                onValueChange = { nameAddress = it },
                label = "نام آدرس",
                topPadding = 20.dp
            )
            CustomTextField(
                value = city,
                onValueChange = { city = it },
                label = "شهر",
                enabled = false,
                topPadding = 20.dp
            )
            CustomTextField(value = street, onValueChange = { street = it }, label = "خیابان")
            CustomTextField(value = milan, onValueChange = { milan = it }, label = "کوچه")
            CustomTextField(value = plate, onValueChange = { plate = it }, label = "پلاک")
            CustomTextField(value = floor, onValueChange = { floor = it }, label = "طبقه")

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = androidx.compose.ui.graphics.Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, end = 10.dp),
                    textAlign = TextAlign.End
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 20.dp),
                onClick = {
                    if (nameAddress.isBlank() || city.isBlank() || street.isBlank() ||
                        milan.isBlank() || plate.isBlank() || floor.isBlank()
                    ) {
                        errorMessage = "تمام فیلدها باید پر شوند"
                        return@Button
                    }

                    errorMessage = ""
                    if (addressId == 0)
                        addNewAddressViewModel.insertAddress(
                            AddressEntity(0, nameAddress, city, street, milan, plate, floor)
                        )
                    else
                        addNewAddressViewModel.updateAddress(
                            AddressEntity(addressId, nameAddress, city, street, milan, plate, floor)
                        )

                    navController.navigate(NavigationScreen.Address.route) {
                        popUpTo(NavigationScreen.Address.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "ذخیره", fontSize = 20.sp)
            }
        }
    }
}
