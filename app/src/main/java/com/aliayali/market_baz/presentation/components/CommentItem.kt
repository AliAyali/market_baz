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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aliayali.market_baz.data.local.database.entity.CommentEntity
import com.aliayali.market_baz.ui.theme.IceMist

@Composable
fun CommentItem(
    comment: CommentEntity,
    userPhone: String,
    isAdmin: Boolean,
    onDelete: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
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
            if (isAdmin || comment.userPhone == userPhone) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        showDialog = true
                    }
                )
            }

            Text(
                text = comment.username
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = comment.detail,
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "حذف نظر",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            text = {
                Text(
                    text = "آیا مطمئن هستید که می‌خواهید این نظر را حذف کنید؟",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDialog = false
                    }
                ) {
                    Text("بله")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("خیر")
                }
            }
        )
    }
}

