package br.com.redcode.base.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import br.com.redcode.base.R
import br.com.redcode.base.extensions.gone
import br.com.redcode.base.extensions.isVisible
import br.com.redcode.base.extensions.toLogcat
import br.com.redcode.base.extensions.visible
import br.com.redcode.base.interfaces.Alertable
import br.com.redcode.base.interfaces.Progressable
import br.com.redcode.base.utils.Alerts
import br.com.redcode.base.utils.Constants.ERROR_API_BACK_TO_PREVIOUS_ACTIVITY
import br.com.redcode.base.utils.Constants.ERROR_API_CLEAN_AND_FORCE_LOGIN
import br.com.redcode.base.utils.Constants.ERROR_API_KEEP_CURRENT_SCREEN
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

/**
 * Created by pedrofsn on 16/10/2017.
 */
abstract class BaseActivity : AppCompatActivity(), LifecycleOwner, Alertable, Progressable {

    abstract val layout: Int
    open var isFullscreen: Boolean = false

    var linearLayoutProgressBar: LinearLayout? = null
    var toolbar: Toolbar? = null

    var processing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isFullscreen) {
            listenerFullScreen()
        }
        super.onCreate(savedInstanceState)
        setupLayout()
        initView()
        middleOnCreate()
        afterOnCreate()
    }

    open fun setupLayout() {
        setContentView(layout)
    }

    open fun middleOnCreate() {

    }

    private fun listenerFullScreen() {
        ativarFullScreen()
        hideToolbar()
        val decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                ativarFullScreen()
            }
        }
    }

    private fun ativarFullScreen() {
        var uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }

        window.decorView.systemUiVisibility = uiOptions
    }

    open fun initView() {
        linearLayoutProgressBar = findViewById(R.id.linearLayoutProgressBar)
        toolbar = findViewById(R.id.toolbar)
        toolbar?.let { setSupportActionBar(it) }
    }

    fun enableHomeAsUpActionBar() = supportActionBar?.setDisplayHomeAsUpEnabled(true)
    fun disableHomeAsUpActionBar() = supportActionBar?.setDisplayHomeAsUpEnabled(false)

    open fun hideToolbar() {
        toolbar?.gone()
        supportActionBar?.hide()
    }

    open fun showToolbar() {
        toolbar?.visible()
        supportActionBar?.show()
    }

    fun restoreShadowToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar?.elevation = 8f
        }
        supportActionBar?.elevation = 8f
    }

    fun removeShadowToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar?.elevation = 0f
        }
        supportActionBar?.elevation = 0f
    }

    override fun toast(message: String?, duration: Int?) {
        message?.let {
            it.toLogcat()
            Toast.makeText(this, it, duration ?: Toast.LENGTH_SHORT).show()
        }
    }

    fun showMessage(message: Int) {
        val messageHandled: String = try {
            getString(message)
        } catch (e: Exception) {
            message.toString()
        }

        getSnackBar(message = messageHandled).show()
    }

    private fun getSnackBar(
        view: View = findViewById(android.R.id.content),
        message: String,
        duration: Int = Snackbar.LENGTH_SHORT
    ): Snackbar {
        "Snackbar: $message".toLogcat()
        return Snackbar.make(view, message, duration)
    }

    fun showOrHideProgress(show: Boolean) = if (show) showProgress() else hideProgress()

    override fun showProgress() {
        runOnUiThread { linearLayoutProgressBar?.visible() }
        processing = true
        getContentView()?.gone()
    }

    override fun hideProgress() {
        if (processing || linearLayoutProgressBar?.isVisible() == true || getContentView()?.isVisible() == false) {
            runOnUiThread { linearLayoutProgressBar?.gone() }
            processing = false
            getContentView()?.visible()
        }
    }

    open fun getContentView(): View? {
        return null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackMenuItemPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    open fun onBackMenuItemPressed() {
        onBackPressed()
    }

    inline fun <reified Activity : AppCompatActivity> goTo(
        params: Pair<String, Any?>? = null,
        requestCode: Int? = null
    ) {
        when {
            requestCode != null && params != null -> {
                val intent = Intent(this, Activity::class.java)
                putExtras(intent, params)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                startActivityForResult(
                    intent,
                    requestCode
                )
            }
            requestCode != null && params == null -> {
                val intent = Intent(this, Activity::class.java)
                putExtras(intent, params)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                startActivityForResult(
                    intent,
                    requestCode
                )
            }
            requestCode == null && params != null -> {
                val intent = Intent(this, Activity::class.java)
                putExtras(intent, params)
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

    inline fun <reified Activity : AppCompatActivity> goTo(vararg params: Pair<String, Any?>) {
        val intent = Intent(this, Activity::class.java)
        putExtras(intent, *params)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
    }

    inline fun <reified Activity : AppCompatActivity> goTo(requestCode: Int, vararg params: Pair<String, Any?>) {
        val intent = Intent(this, Activity::class.java)
        putExtras(intent, *params)
        startActivityForResult(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), requestCode)
    }

    inline fun <reified Activity : AppCompatActivity> goToWithNoHistory(
        vararg params: Pair<String, Any?>,
        beforeStartActivity: (() -> Unit)
    ) {
        val intent = Intent(this, Activity::class.java)
        putExtras(intent, *params)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        beforeStartActivity.invoke()
    }

    fun goToWithNoHistory(activity: Class<*>, pair: Pair<String, Int>? = null) {
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        pair?.let { intent.putExtra(pair.first, pair.second) }
        startActivity(intent)
    }

    fun changeFragment(fragment: Fragment, id: Int, tag: String? = null) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(id, fragment)
        tag?.let { fragmentTransaction.addToBackStack(tag) }
        fragmentTransaction.commit()
    }

    fun putExtras(intent: Intent, vararg params: Pair<String, Any?>?): Intent {
        params.forEach { p ->
            p?.apply {
                if (first.isNotBlank()) {
                    when (second) {
                        is Parcelable -> intent.putExtra(first, second as Parcelable)
                        is Byte -> intent.putExtra(first, second as Byte)
                        is CharSequence -> intent.putExtra(first, second as CharSequence)
                        is Int -> intent.putExtra(first, second as Int)
                        is Bundle -> intent.putExtra(first, second as Bundle)
                        is Double -> intent.putExtra(first, second as Double)
                        is Boolean -> intent.putExtra(first, second as Boolean)
                        is String -> intent.putExtra(first, second as String)
                        is Long -> intent.putExtra(first, second as Long)
                        is Char -> intent.putExtra(first, second as Char)
                        is Serializable -> intent.putExtra(first, second as Serializable)
                        is Float -> intent.putExtra(first, second as Float)
                        is Short -> intent.putExtra(first, second as Short)
                        is Array<*> -> {
                            (second as? Array<Long>)?.let { intent.putExtra(first, it) }
                            (second as? Array<Double>)?.let { intent.putExtra(first, it) }
                            (second as? Array<Boolean>)?.let { intent.putExtra(first, it) }
                            (second as? Array<Char>)?.let { intent.putExtra(first, it) }
                            (second as? Array<Byte>)?.let { intent.putExtra(first, it) }
                            (second as? Array<Parcelable>)?.let { intent.putExtra(first, it) }
                            (second as? Array<CharSequence>)?.let { intent.putExtra(first, it) }
                            (second as? Array<Float>)?.let { intent.putExtra(first, it) }
                            (second as? Array<Int>)?.let { intent.putExtra(first, it) }
                            (second as? Array<String>)?.let { intent.putExtra(first, it) }
                            (second as? Array<Short>)?.let { intent.putExtra(first, it) }
                        }
                        is ArrayList<*> -> {
                            (second as? ArrayList<CharSequence>)?.let {
                                intent.putCharSequenceArrayListExtra(
                                    first,
                                    it
                                )
                            }
                            (second as? ArrayList<Int>)?.let { intent.putIntegerArrayListExtra(first, it) }
                            (second as? ArrayList<Parcelable>)?.let { intent.putParcelableArrayListExtra(first, it) }
                            (second as? ArrayList<String>)?.let { intent.putStringArrayListExtra(first, it) }
                        }
                    }
                }
            }
        }

        return intent
    }

    abstract fun afterOnCreate()

    fun onBackPressed(view: View?) = onBackPressed()

    override fun onBackPressed() {
        if (isProcessingWithAlert().not()) {
            super.onBackPressed()
        }
    }

    fun isProcessingWithAlert(): Boolean {
        if (processing) {
            showMessage(getString(R.string._wait))
        }
        return processing
    }

    open fun handleActionAPI(action: Int, id: String) {
        when (action) {
            ERROR_API_KEEP_CURRENT_SCREEN -> hideProgress()
            ERROR_API_BACK_TO_PREVIOUS_ACTIVITY -> finish()
            ERROR_API_CLEAN_AND_FORCE_LOGIN -> clearLocalDataAndGoToLoginScreen()
        }
    }

    open fun clearLocalDataAndGoToLoginScreen() {}

    override fun showSimpleAlert(message: String, function: (() -> Unit)?) {
        Alerts.showDialogOk(this, getString(R.string.aviso), message, onOk = { function?.invoke() })
    }

    override fun showMessage(message: String, duration: Int) =
        getSnackBar(message = message, duration = duration).show()

    override fun finish() {
        super.finish()
    }

}