package br.com.redcode.base.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.webkit.URLUtil
import br.com.redcode.base.R
import br.com.redcode.base.utils.Constants
import br.com.redcode.base.utils.Constants.SDF_BRAZILIAN_DATE
import timber.log.Timber
import java.io.File
import java.security.MessageDigest
import java.util.*


/**
 * Created by pedrofsn on 01/12/17.
 */

fun String.toCalendarWithZeroTime(): Calendar? {
    return try {
        val date = SDF_BRAZILIAN_DATE.parse(this)
        val calendar = Calendar.getInstance()

        calendar.time = date
        calendar.zeroTime()
    } catch (e: Exception) {
        null
    }
}

fun String.toTime24h(): Calendar? {
    return try {
        val date = Constants.SDF_TIME_24H.parse(this)
        val calendar = Calendar.getInstance()

        calendar.time = date
        calendar
    } catch (e: Exception) {
        null
    }
}

fun String.shareText(context: Context, labelInstruction: String) {
    val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, this)
    context.startActivity(Intent.createChooser(sharingIntent, labelInstruction))
}

fun String.parseColor() = Color.parseColor(this)

fun String.toPHPMoneyValue() = this.replace("R$ ", "").replace(".", "").replace(",", ".")
fun String.toPHPMoneyValueInDouble(): Double {
    var value = 0.0
    try {
        if (this.isNotBlank()) {
            val money = toPHPMoneyValue()
            value = money.toDouble()
        }
    } catch (e: Exception) {

    }

    return value
}

fun String.copyToClipboard(context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", this)
    clipboard.setPrimaryClip(clip)
}

fun String.sha256(): String {
    try {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(toByteArray(Charsets.UTF_8))
        val hexString = StringBuffer()

        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }

        return hexString.toString()
    } catch (ex: Exception) {
        throw RuntimeException(ex)
    }
}

fun String.toUpperCaseAndApplySHA256() = sha256().toUpperCase()

fun String?.toLogcat() = this?.let { if (it.isNotBlank()) Timber.e(it) }

fun String.openLinkInBrowser(context: Context) {
    if (isNotBlank()) {

        val url = if (!startsWith("http://") && !startsWith("https://")) {
            "http://$this"
        } else {
            this
        }

        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}

fun String.isURL(): Boolean {
    return URLUtil.isHttpUrl(this) || URLUtil.isHttpsUrl(this)
}

fun File.formatSize() = length().formatSize()

fun Long.formatSize(): String {
    // Get length of file in bytes
    val fileSizeInBytes = this

    val fileSizeInKB = fileSizeInBytes / 1024

    return fileSizeInKB.formatSizeFromKb()
}

fun Long.formatSizeFromKb(): String {
    val fileSizeInKB = this

    // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
    val fileSizeInMB = fileSizeInKB / 1024

    return when {
        fileSizeInMB > 0 -> "$fileSizeInMB MB"
        else -> "$fileSizeInKB KB"
    }
}