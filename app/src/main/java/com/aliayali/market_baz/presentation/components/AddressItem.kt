package com.aliayali.market_baz.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliayali.market_baz.ui.theme.BrightOrange

@Composable
fun AddressItem(
    title: String,
    detail: String,
    onEdite: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF0F5FA),
                RoundedCornerShape(3.dp)
            )
            .padding(top = 10.dp, end = 10.dp, start = 10.dp, bottom = 10.dp)
            .height(80.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    Icons.Default.Delete,
                    null,
                    tint = BrightOrange,
                    modifier = Modifier.clickable {
                        onDelete()
                    }
                )
                Spacer(Modifier.width(15.dp))
                Icon(
                    Icons.Default.Edit,
                    null,
                    tint = BrightOrange,
                    modifier = Modifier.clickable {
                        onEdite()
                    }
                )
            }
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.End,
                fontSize = 20.sp
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            text = detail,
            textAlign = TextAlign.End
        )
    }

}