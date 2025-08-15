package com.aliayali.market_baz.presentation.screens.verification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.ui.AuthenticationHeader
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun VerificationScreen(
    navController: NavController,
    verificationViewModel: VerificationViewModel = hiltViewModel(),
) {

    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var progress by remember { mutableStateOf(false) }

    AuthenticationHeader(
        "تاییدیه",
        "ما یک کد به تلفن شما ارسال کرده ایم"
    ) {

        BasicTextField(
            value = text,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    text = it
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .size(1.dp)
                .focusRequester(focusRequester),
            textStyle = TextStyle(color = Color.Transparent)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    focusRequester.requestFocus()
                }
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (verificationViewModel.resend.value > 0) {
                Text(
                    text = "ثانیه دیگر",
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "${verificationViewModel.resend.value}",
                )
            }
            Spacer(Modifier.width(5.dp))
            Text(
                text = "ارسال دوباره",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                color =
                    if (verificationViewModel.resend.value > 0)
                        Color.Gray
                    else
                        Color.Black,
                modifier = Modifier.clickable {
                    if (verificationViewModel.resend.value == 0) {
                        verificationViewModel.timerPlus()
                    }
                }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    focusRequester.requestFocus()
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5) { index ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(IceMist, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text.getOrNull(index)?.toString() ?: "",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.width(10.dp))
            }
        }


        Button(
            onClick = {
                progress = true
                navController.navigate(NavigationScreen.Home.route) {
                    popUpTo(NavigationScreen.Verification.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            enabled = true
        ) {
            Row {
                if (progress)
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary
                    )
                else
                    Text(
                        text = "تایید",
                        fontSize = 20.sp
                    )
            }
        }

    }

}
