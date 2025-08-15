package com.aliayali.market_baz.presentation.screens.forgotPassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aliayali.market_baz.core.utils.isValidPhoneNumber
import com.aliayali.market_baz.core.utils.normalizePhoneNumber
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.ui.AuthenticationHeader
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
) {
    var phone by remember { mutableStateOf("") }
    var progress by remember { mutableStateOf(false) }

    AuthenticationHeader(
        "فراموش کردن رمز",
        "لطفا شماره همراه خود را وارد کنید"
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = phone,
            onValueChange = {
                phone = normalizePhoneNumber(it)
            },
            label = {
                Text(
                    text = "شماره همراه",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            isError = !isValidPhoneNumber(phone),
            textStyle = TextStyle(
                textAlign = TextAlign.Start
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Phone, null
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = IceMist,
                focusedIndicatorColor = IceMist,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = {
                progress = true
                navController.navigate(NavigationScreen.Verification.route) {
                    popUpTo(NavigationScreen.ForgotPassword.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            enabled = phone.isNotBlank() && isValidPhoneNumber(phone)
        ) {
            Row {
                if (progress)
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary
                    )
                else
                    Text(
                        text = "ارسال کد",
                        fontSize = 20.sp
                    )
            }
        }

        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ثبت نام کنید",
                modifier = Modifier
                    .clickable {
                        navController.navigate(NavigationScreen.Signup.route) {
                            popUpTo(NavigationScreen.ForgotPassword.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "حساب کاربری ندارید؟",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }

}
