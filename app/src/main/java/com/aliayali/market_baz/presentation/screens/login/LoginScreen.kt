package com.aliayali.market_baz.presentation.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.collectAsState
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
    val uiState by loginViewModel.uiState.collectAsState()

    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    AuthenticationHeader(
        title = "وارد شوید",
        detail = "لطفا وارد حساب کاربری خود شوید"
    ) {
        uiState.errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        TextField(
            value = phone,
            onValueChange = {
                phone = normalizePhoneNumber(it)
                loginViewModel.clearError()
            },
            label = {
                Text(
                    "شماره همراه",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            leadingIcon = { Icon(Icons.Default.Phone, null) },
            isError = !isValidPhoneNumber(phone),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = IceMist,
                focusedIndicatorColor = IceMist,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(textAlign = TextAlign.Start)
        )

        TextField(
            value = password,
            onValueChange = {
                password = it
                loginViewModel.clearError()
            },
            label = {
                Text(
                    "رمز عبور",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
            ),
            textStyle = TextStyle(textAlign = TextAlign.Start)
        )

        Button(
            onClick = { loginViewModel.getUserByPhone(phone) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            enabled = phone.isNotBlank() && password.isNotBlank() && isValidPhoneNumber(phone)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("ورود", fontSize = 20.sp)
            }
        }

        LaunchedEffect(uiState.user) {
            uiState.user?.let { user ->
                if (user.password == password) {
                    loginViewModel.login(user)
                } else {
                    loginViewModel.clearLoginSuccess()
                    loginViewModel.clearError()
                    loginViewModel.setError("رمز اشتباه است")
                }
            }
        }

        LaunchedEffect(uiState.isLoggedIn) {
            if (uiState.isLoggedIn) {
                navController.navigate(NavigationScreen.Verification.route) {
                    popUpTo(NavigationScreen.Login.route) { inclusive = true }
                    launchSingleTop = true
                }
                loginViewModel.clearLoginSuccess()
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ثبت نام کنید",
                modifier = Modifier.clickable {
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

        Spacer(modifier = Modifier.height(50.dp))
    }
}


