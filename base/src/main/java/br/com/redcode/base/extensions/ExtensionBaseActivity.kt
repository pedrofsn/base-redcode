package br.com.redcode.base.extensions

import android.content.Intent
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.redcode.base.activities.BaseActivity

/*
    CREATED BY @PEDROFSN
*/


fun BaseActivity.replace(@IdRes id: Int, fragment: Fragment) = supportFragmentManager
        .beginTransaction()
        .replace(id, fragment)
        .commit()

fun BaseActivity.change(@IdRes id: Int, fragment: Fragment) = supportFragmentManager
        .beginTransaction()
        .add(id, fragment)
        .addToBackStack("teste")
        .commit()

fun BaseActivity.hasFragmentNavigation() = supportFragmentManager.backStackEntryCount > 0

fun BaseActivity.createIntent(clazz: Class<*>, vararg params: Pair<String, Any?>): Intent {
    val intent = Intent(this, clazz)
    putExtras(intent, *params)
    return intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
}

fun BaseActivity.goTo(clazz: Class<*>, vararg params: Pair<String, Any?>) {
    val intent = createIntent(clazz, *params)
    startActivity(intent)
}

fun BaseActivity.goTo(requestCode: Int, clazz: Class<*>, vararg params: Pair<String, Any?>) {
    val intent = createIntent(clazz, *params)
    startActivityForResult(intent, requestCode)
}

fun BaseActivity.goTo(
        clazz: Class<*>,
        params: Pair<String, Any?>? = null,
        requestCode: Int? = null
) {
    when {
        requestCode != null && params != null -> {
            val intent = Intent(this, clazz)
            putExtras(intent, params)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivityForResult(
                    intent,
                    requestCode
            )
        }

        requestCode != null && params == null -> {
            val intent = Intent(this, clazz)
            putExtras(intent, params)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivityForResult(
                    intent,
                    requestCode
            )
        }

        requestCode == null && params != null -> {
            val intent = Intent(this, clazz)
            putExtras(intent, params)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(intent)
        }

        requestCode == null && params == null -> {
            val intent = Intent(this, clazz)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(intent)
        }
    }
}

fun AppCompatActivity.receiveDouble(name: String) = lazy {
    intent?.getDoubleExtra(name, 0.0) ?: 0.0
}