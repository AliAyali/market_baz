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

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rePasswordVisible by remember { mutableStateOf(false) }
    val passwordsMatch = password == rePassword || rePassword.isEmpty()
    var progress by remember { mutableStateOf(false) }
    val user by signupViewModel.user
    val error by signupViewModel.error

    LaunchedEffect(user, progress) {
        if (progress) {
            if (user == null) {
                signupViewModel.insertUser(phone, name, password)
                progress = false
                signupViewModel.savePhone(phone)
                navController.navigate(NavigationScreen.Verification.route) {
                    popUpTo(NavigationScreen.Signup.route) { inclusive = true }
                    launchSingleTop = true
                }
            } else {
                signupViewModel.setError("این شماره از قبل وجود دارد")
                progress = false
            }
        }
    }
    AuthenticationHeader(
        "ثبت نام کنید",
        "لطفا برای شروع ثبت نام کنید"
    ) {

        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text(
                    text = "نام",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            enabled = true,
            isError = false,
            textStyle = TextStyle(
                textAlign = TextAlign.End
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Person, null
                )
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
            enabled = true,
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
            isError = !passwordsMatch,
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
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = rePassword,
            onValueChange = { it ->
                rePassword = it
            },
            label = {
                Text(
                    text = "رمز عبور را دوباره تایپ کنید",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            enabled = true,
            isError = !passwordsMatch,
            textStyle = TextStyle(
                textAlign = TextAlign.Start
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Lock, null
                )
            },
            visualTransformation = if (rePasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val icon = if (rePasswordVisible) painterResource(R.drawable.ic_visibility)
                else painterResource(R.drawable.ic_visibility_off)
                IconButton(onClick = { rePasswordVisible = !rePasswordVisible }) {
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

        Button(
            onClick = {
                progress = true
                signupViewModel.getDataByPhone(phone)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            enabled = name.isNotBlank() && phone.isNotBlank() &&
                    password.isNotBlank() && rePassword.isNotBlank() &&
                    passwordsMatch && isValidPhoneNumber(phone),
        ) {
            Row {
                if (progress)
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                else
                    Text(text = "ثبت نام", fontSize = 20.sp)
            }
        }


        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "وارد شوید",
                modifier = Modifier
                    .clickable {
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

