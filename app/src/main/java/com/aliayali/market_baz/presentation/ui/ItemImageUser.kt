package com.aliayali.market_baz.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ItemImageUser(
    image: Int,
    onClick: () -> Unit,
) {

    Image(
        painterResource(image),
        null,
        modifier = Modifier
            .size(100.dp)
            .clickable {
                onClick()
            }
    )

}