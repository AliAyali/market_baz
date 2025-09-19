package com.aliayali.market_baz.presentation.screens.admin.components

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()
        inputStream?.close()
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}