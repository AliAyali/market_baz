package com.aliayali.market_baz.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aliayali.market_baz.ui.theme.IceMist

@Preview(
    showBackground = true,
    widthDp = 500,
    heightDp = 1000
)
@Composable
fun CommentItem() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
            .background(
                IceMist,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.MoreVert,
                null
            )
            Text(
                text = "نام کاربری"
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "متن",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }

}