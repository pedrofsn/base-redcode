package br.com.redcode.base.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import br.com.redcode.base.models.ErrorAPI
import br.com.redcode.base.utils.Constants

/*
    CREATED BY @PEDROFSN
*/


inline fun <reified T : Enum<T>> safeEnum(value: String, default: T): T = try {
    java.lang.Enum.valueOf(T::class.java, value)
} catch (e: Exception) {
    default
}

fun ErrorAPI.isWithoutErrorInformation() = (isOk().not() && isError().not()) || isOk()
fun ErrorAPI.getSafeMessage() = extract safe msg

fun onBackToStartFlux(data: Intent?) = data?.has("backToStartFlux") == true

fun <T : Parcelable> AppCompatActivity.receive(name: String): Lazy<T?> = lazy {
    intent?.getParcelableExtra<T>(name)
}

fun getId(data: Intent?): Long {
    val default = Constants.INVALID_VALUE.toLong()
    return data?.getLongExtra("id", default) ?: default
}

@SuppressLint("HardwareIds")
fun Context.getIdentifier() = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

