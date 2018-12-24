package br.com.redcode.base.extensions

import android.graphics.Bitmap
import android.util.Base64
import android.util.Base64.encodeToString
import java.io.ByteArrayOutputStream
import java.io.File

fun Bitmap.toBase64(format: Bitmap.CompressFormat? = null, quality: Int? = null): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(format ?: Bitmap.CompressFormat.JPEG, quality ?: 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun File.toBase64(): String = encodeToString(readBytes(), Base64.DEFAULT)