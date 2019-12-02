package br.com.redcode.base.extensions

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

/*
    CREATED BY @PEDROFSN
*/

fun Intent.putExtras(vararg parameters: Pair<String, Any?>?): Intent {
    parameters.forEach { parameter ->
        parameter?.apply {
            if (first.isNotBlank()) {
                when (second) {
                    is Parcelable -> putExtra(first, second as Parcelable)
                    is Byte -> putExtra(first, second as Byte)
                    is CharSequence -> putExtra(first, second as CharSequence)
                    is Int -> putExtra(first, second as Int)
                    is Bundle -> putExtra(first, second as Bundle)
                    is Double -> putExtra(first, second as Double)
                    is Boolean -> putExtra(first, second as Boolean)
                    is String -> putExtra(first, second as String)
                    is Long -> putExtra(first, second as Long)
                    is Char -> putExtra(first, second as Char)
                    is Serializable -> putExtra(first, second as Serializable)
                    is Float -> putExtra(first, second as Float)
                    is Short -> putExtra(first, second as Short)
                    is Array<*> -> {
                        (second as? Array<Long>)?.let { putExtra(first, it) }
                        (second as? Array<Double>)?.let { putExtra(first, it) }
                        (second as? Array<Boolean>)?.let { putExtra(first, it) }
                        (second as? Array<Char>)?.let { putExtra(first, it) }
                        (second as? Array<Byte>)?.let { putExtra(first, it) }
                        (second as? Array<Parcelable>)?.let { putExtra(first, it) }
                        (second as? Array<CharSequence>)?.let { putExtra(first, it) }
                        (second as? Array<Float>)?.let { putExtra(first, it) }
                        (second as? Array<Int>)?.let { putExtra(first, it) }
                        (second as? Array<String>)?.let { putExtra(first, it) }
                        (second as? Array<Short>)?.let { putExtra(first, it) }
                    }
                    is ArrayList<*> -> {
                        (second as? ArrayList<CharSequence>)?.let {
                            putCharSequenceArrayListExtra(first, it)
                        }
                        (second as? ArrayList<Int>)?.let {
                            putIntegerArrayListExtra(first, it)
                        }
                        (second as? ArrayList<Parcelable>)?.let {
                            putParcelableArrayListExtra(first, it)
                        }
                        (second as? ArrayList<String>)?.let {
                            putStringArrayListExtra(first, it)
                        }
                    }
                }
            }
        }
    }

    return this
}

inline fun <reified Activity : AppCompatActivity> AppCompatActivity.goTo(
    params: Pair<String, Any?>? = null,
    requestCode: Int? = null
) {
    when {
        requestCode != null && params != null -> {
            val intent = Intent(this, Activity::class.java)
            intent.putExtras(params)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivityForResult(
                intent,
                requestCode
            )
        }
        requestCode != null && params == null -> {
            val intent = Intent(this, Activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivityForResult(
                intent,
                requestCode
            )
        }
        requestCode == null && params != null -> {
            val intent = Intent(this, Activity::class.java)
            intent.putExtras(params)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(intent)
        }
        requestCode == null && params == null -> {
            val intent = Intent(this, Activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(intent)
        }
    }
}

inline fun <reified Activity : AppCompatActivity> AppCompatActivity.goTo(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) {
    val intent = Intent(this, Activity::class.java)
    intent.putExtras(*params)
    startActivityForResult(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), requestCode)
}