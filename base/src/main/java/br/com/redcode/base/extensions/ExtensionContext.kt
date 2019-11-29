package br.com.redcode.base.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings

/*
    CREATED BY @PEDROFSN
*/

fun Context.openSettings() {
    val intent = Intent()
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", getPackageName())
            intent.putExtra("app_uid", getApplicationInfo().uid)
        }
        else -> {
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:" + getPackageName())
        }
    }
    startActivity(intent)
}

fun Context.openAppInPlayStore(appPackageName: String = packageName) {
    val uri = try {
        Uri.parse("market://details?id=$appPackageName")
    } catch (excpetion: ActivityNotFoundException) {
        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
    }
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
}

fun Context.isPackageInstalled(mPackageName: String = packageName) = try {
    packageManager.getPackageInfo(mPackageName, 0)
    true
} catch (e: PackageManager.NameNotFoundException) {
    false
}
