package com.aliayali.market_baz.presentation.screens.admin.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.InputStream

@Composable
fun GalleryImagePickerProduct(
    selectedImageUri: Uri?,
    onImageSelected: (Uri?) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageSelected(uri)
    }

    val bitmap: Bitmap? by produceState<Bitmap?>(initialValue = null, key1 = selectedImageUri) {
        value = null
        selectedImageUri?.let { uri ->
            try {
                val stream: InputStream? = context.contentResolver.openInputStream(uri)
                value = stream?.use { BitmapFactory.decodeStream(it) }
            } catch (e: Exception) {
                value = null
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = "Selected image",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No image", modifier = Modifier.padding(4.dp))
                }
            }
        }

        Spacer(Modifier.width(12.dp))

        Column {
            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "انتخاب عکس")
            }
            Spacer(Modifier.height(6.dp))
            androidx.compose.material3.TextButton(onClick = { onImageSelected(null) }) {
                Text(text = "حذف عکس")
            }
        }
    }
}
