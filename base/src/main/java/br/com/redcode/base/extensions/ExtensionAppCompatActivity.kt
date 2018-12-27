package br.com.redcode.base.extensions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.redcode.base.R
import br.com.redcode.base.utils.Constants.EMPTY_STRING
import br.com.redcode.base.utils.Constants.INVALID_VALUE
import kotlinx.android.synthetic.main.ui_toolbar.*

/**
 * Created by pedrofsn on 17/10/2017.
 */

fun AppCompatActivity.setColorToolbar(@ColorRes colorIntRes: Int): Int {
    val color = ContextCompat.getColor(this, colorIntRes)

    supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
    toolbar?.setBackgroundColor(color)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    return color
}

fun AppCompatActivity.setColorToolbar(hex: String): Int {
    val color = Color.parseColor(hex)

    supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
    toolbar?.setBackgroundColor(color)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    return color
}

fun AppCompatActivity.setCustomTitleToolbar(@StringRes string: Int) = setCustomTitleToolbar(getString(string))

fun AppCompatActivity.setCustomTitleToolbar(string: String?) {
    if (string?.isBlank() == false) {
        supportActionBar?.let { it.title = string }
    }
}

fun AppCompatActivity.setSubtituloToolbar(string: String?) {
    if (string?.isBlank() == false) {
        supportActionBar?.let { it.subtitle = string }
    }
}

fun AppCompatActivity.openAppInPlayStore() {
    val intent = try {
        Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
    } catch (excpetion: ActivityNotFoundException) {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
    }
    startActivity(intent)
}

fun AppCompatActivity.openNotificationSettings() {
    try {
        val intent = Intent()

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                Toast.makeText(this, getString(R.string.message_open_settings), Toast.LENGTH_LONG).show()
                startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                return
            }

            Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1 -> {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra("app_package", packageName)
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", packageName)
                intent.putExtra("app_uid", applicationInfo.uid)
            }

            Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT -> {
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("package:$packageName")
            }
            else -> return
        }

        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun AppCompatActivity.isEnabled(permission: String) =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.isEnabled(vararg permissions: String): Boolean {
    if (permissions.isNotEmpty()) {
        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
    } else {
        return false
    }

    return true
}

fun AppCompatActivity.lazyId() = receiveLong("id")

fun AppCompatActivity.receiveInt(name: String): Lazy<Int> = lazy {
    intent?.getIntExtra(name, INVALID_VALUE) ?: INVALID_VALUE
}

fun AppCompatActivity.receiveLong(name: String): Lazy<Long> = lazy {
    intent?.getLongExtra(name, INVALID_VALUE.toLong()) ?: INVALID_VALUE.toLong()
}

fun AppCompatActivity.receiveBoolean(name: String, default: Boolean = false): Lazy<Boolean> = lazy {
    intent?.getBooleanExtra(name, default) ?: default
}

fun AppCompatActivity.receiveString(name: String): Lazy<String> = lazy {
    intent?.getStringExtra(name) ?: EMPTY_STRING
}

/*

infix fun <T> AppCompatActivity.receive(name: String) : Lazy<T> = lazy {
    return@lazy when(T) {
       is Int -> intent?.getIntExtra(name, INVALID_VALUE) ?: INVALID_VALUE
       is String -> intent?.getStringExtra(name) ?: EMPTY_STRING
        else -> throw RuntimeException("Invalid Type for 'fast' lazy load")
    }
} as Lazy<T>

* */