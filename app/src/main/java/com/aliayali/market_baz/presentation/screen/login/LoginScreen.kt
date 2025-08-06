package com.aliayali.market_baz.presentation.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.R
import com.aliayali.market_baz.core.utils.isValidPhoneNumber
import com.aliayali.market_baz.core.utils.normalizePhoneNumber
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.ui.AuthenticationHeader
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val user by loginViewModel.user
    val error = loginViewModel.error
    var progress by loginViewModel.progress

    AuthenticationHeader(
        "وارد شوید",
        "لطفا وارد حساب کاربری موجود خود شوید",
    ) {
        Text(
            text = error.value,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

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

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(
                    text = "رمز",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            enabled = true,
            isError = false,
            textStyle = TextStyle(
                textAlign = TextAlign.Start
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Lock, null
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val icon = if (passwordVisible) painterResource(R.drawable.ic_visibility)
                else painterResource(R.drawable.ic_visibility_off)
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = icon, contentDescription = "Visibility")
                }
            },
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = IceMist,
                focusedIndicatorColor = IceMist,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Text(
            text = "فراموش کردن رمز عبور",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(NavigationScreen.ForgotPassword.route) {
                        popUpTo(NavigationScreen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.primary
        )

        Button(
            onClick = {
                loginViewModel.changeProgress(true)
                loginViewModel.getUserByPhone(phone)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            enabled = phone.isNotBlank() && password.isNotBlank() && isValidPhoneNumber(phone)
        ) {
            Row {
                if (progress)
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary
                    )
                else
                    Text(text = "ورود", fontSize = 20.sp)
            }

        }

        LaunchedEffect(user) {
            user?.let {
                if (it.password == password) {
                    loginViewModel.loginState(true)
                    loginViewModel.savePhone(phone)
                    navController.navigate(NavigationScreen.Verification.route) {
                        popUpTo(NavigationScreen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                } else {
                    loginViewModel.setError("رمز اشتباه است")
                }
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
                            popUpTo(NavigationScreen.Login.route) { inclusive = true }
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

