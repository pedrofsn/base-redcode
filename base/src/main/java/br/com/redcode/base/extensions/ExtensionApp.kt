package br.com.redcode.base.extensions

import android.content.Intent
import br.com.redcode.base.models.ErrorAPI

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

fun Intent?.has(param: String, default: Boolean = false): Boolean {
    return (this as Intent).getBooleanExtra(param, default)
}