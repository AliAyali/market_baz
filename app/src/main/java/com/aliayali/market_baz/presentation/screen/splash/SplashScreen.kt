package com.aliayali.market_baz.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aliayali.market_baz.R

@Preview(
    showBackground = true,
    widthDp = 300,
    heightDp = 600
)
@Composable
fun SplashScreen() {

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painterResource(R.drawable.splash_top),
            null,
            Modifier.align(Alignment.Start)
        )
        Image(
            painterResource(R.drawable.logo_app),
            null,
            Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp)
        )
        Image(
            painterResource(R.drawable.splash_bottom),
            null,
            Modifier.align(Alignment.End)
        )
    }

}