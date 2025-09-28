package com.aliayali.market_baz.presentation.screens.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
fun SignupScreen(
    navController: NavController,
    signupViewModel: SignupViewModel = hiltViewModel(),
) {
    val uiState by signupViewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rePasswordVisible by remember { mutableStateOf(false) }
    val passwordsMatch = password == rePassword || rePassword.isEmpty()

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            println("Error: $message")
        }
    }

    LaunchedEffect(uiState.isUserCreated) {
        if (uiState.isUserCreated) {
            navController.navigate(NavigationScreen.Verification.route) {
                popUpTo(NavigationScreen.Signup.route) { inclusive = true }
                launchSingleTop = true
            }
            signupViewModel.clearSuccess()
        }
    }

    AuthenticationHeader(
        "ثبت نام کنید",
        "لطفا برای شروع ثبت نام کنید"
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
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                name = it
                signupViewModel.clearError()
            },
            label = {
                Text(
                    text = "نام", fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = IceMist,
                focusedIndicatorColor = IceMist,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(textAlign = TextAlign.End)
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = phone,
            onValueChange = {
                phone = normalizePhoneNumber(it)
                signupViewModel.clearError()
            },
            label = {
                Text(
                    text = "شماره همراه", fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            leadingIcon = { Icon(Icons.Default.Phone, null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = !isValidPhoneNumber(phone),
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
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
                signupViewModel.clearError()
            },
            label = {
                Text(
                    "رمز", fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon = if (passwordVisible) painterResource(R.drawable.ic_visibility)
                    else painterResource(R.drawable.ic_visibility_off)
                    Icon(painter = icon, contentDescription = "Visibility")
                }
            },
            isError = !passwordsMatch,
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = IceMist,
                focusedIndicatorColor = IceMist,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(textAlign = TextAlign.End)
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = rePassword,
            onValueChange = {
                rePassword = it
                signupViewModel.clearError()
            },
            label = {
                Text(
                    "رمز عبور را دوباره تایپ کنید", fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            visualTransformation = if (rePasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { rePasswordVisible = !rePasswordVisible }) {
                    val icon = if (rePasswordVisible) painterResource(R.drawable.ic_visibility)
                    else painterResource(R.drawable.ic_visibility_off)
                    Icon(painter = icon, contentDescription = "Visibility")
                }
            },
            isError = !passwordsMatch,
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = IceMist,
                focusedIndicatorColor = IceMist,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(textAlign = TextAlign.End)
        )

        Button(
            onClick = {
                signupViewModel.getDataByPhone(phone)
                signupViewModel.signup(phone, name, password)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            enabled = name.isNotBlank() && phone.isNotBlank() &&
                    password.isNotBlank() && rePassword.isNotBlank() &&
                    passwordsMatch && isValidPhoneNumber(phone),
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                )
            } else {
                Text(text = "ثبت نام", fontSize = 20.sp)
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "وارد شوید",
                modifier = Modifier.clickable {
                    navController.navigate(NavigationScreen.Login.route) {
                        popUpTo(NavigationScreen.Signup.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "حساب کاربری دارید؟",
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        Spacer(Modifier.height(50.dp))
    }
}