package br.com.redcode.base.mvvm.extensions

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager

@SuppressLint("MissingPermission")
fun Context.isOnline(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = cm.activeNetworkInfo
    val status = networkInfo != null && networkInfo.isConnected
    return status
}

fun Context.getAppVersionName(): String {
    return try {
        val comp = ComponentName(this, this.packageName)
        val pinfo = this.packageManager.getPackageInfo(comp.packageName, 0)
        pinfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "ND"
    }
}
