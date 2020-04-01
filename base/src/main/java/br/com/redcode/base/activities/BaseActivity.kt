package br.com.redcode.base.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import br.com.redcode.base.R
import br.com.redcode.base.extensions.gone
import br.com.redcode.base.extensions.putExtras
import br.com.redcode.base.extensions.toLogcat
import br.com.redcode.base.extensions.visible
import br.com.redcode.base.fragments.getSafeString
import br.com.redcode.base.interfaces.Alertable
import br.com.redcode.base.interfaces.Progressable
import br.com.redcode.base.utils.Alerts
import br.com.redcode.base.utils.Constants.ERROR_API_BACK_TO_PREVIOUS_ACTIVITY
import br.com.redcode.base.utils.Constants.ERROR_API_CLEAN_AND_FORCE_LOGIN
import br.com.redcode.base.utils.Constants.ERROR_API_KEEP_CURRENT_SCREEN
import com.google.android.material.snackbar.Snackbar

/**
 * Created by pedrofsn on 16/10/2017.
 */
abstract class BaseActivity : AppCompatActivity(), Alertable, Progressable {

    abstract val layout: Int
    open var isFullscreen: Boolean = false

    var linearLayoutProgressBar: LinearLayout? = null
    var toolbar: Toolbar? = null

    override var processing: Boolean = false

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

    fun showMessage(message: Int) {
        val messageHandled: String = try {
            getString(message)
        } catch (e: Exception) {
            message.toString()
        }

        getSnackBar(message = messageHandled).show()
    }

    fun getSnackBar(
        view: View = findViewById(android.R.id.content),
        message: String,
        duration: Int = Snackbar.LENGTH_SHORT
    ): Snackbar {
        "Snackbar: $message".toLogcat()
        return Snackbar.make(view, message, duration)
    }

    fun showOrHideProgress(show: Boolean) = if (show) showProgress() else hideProgress()

    override fun showProgress() = runOnUiThread {
        linearLayoutProgressBar?.visible()
        processing = true
        getContentView()?.gone()
    }

    override fun hideProgress() = runOnUiThread {
        linearLayoutProgressBar?.gone()
        processing = false
        getContentView()?.visible()
    }

    open fun getContentView(): View? {
        return null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackMenuItemPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    open fun onBackMenuItemPressed() {
        onBackPressed()
    }

    inline fun <reified Activity : AppCompatActivity> goToWithNoHistory(
        vararg params: Pair<String, Any?>,
        beforeStartActivity: (() -> Unit)
    ) {
        val intent = Intent(this, Activity::class.java)
        intent.putExtras(*params)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        beforeStartActivity.invoke()
    }

    fun goToWithNoHistory(activity: Class<*>, vararg params: Pair<String, Any?>) {
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtras(*params)
        startActivity(intent)
    }

    fun changeFragment(fragment: Fragment, id: Int, tag: String? = null) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(id, fragment)
        tag?.let { fragmentTransaction.addToBackStack(tag) }
        fragmentTransaction.commit()
    }

    abstract fun afterOnCreate()

    fun onBackPressed(view: View?) = onBackPressed()

    override fun onBackPressed() {
        if (canRunBackPressed()) {
            super.onBackPressed()
        }
    }

    open fun canRunBackPressed() = isProcessingWithAlert().not()

    open fun isProcessingWithAlert(): Boolean {
        if (processing) {
            showMessageProcessing()
        }
        return processing
    }

    open fun showMessageProcessing() {
        showMessage(getString(R.string._wait))
    }

    open fun handleActionAPI(action: Int, id: String) {
        when (action) {
            ERROR_API_KEEP_CURRENT_SCREEN -> hideProgress()
            ERROR_API_BACK_TO_PREVIOUS_ACTIVITY -> finish()
            ERROR_API_CLEAN_AND_FORCE_LOGIN -> clearLocalDataAndGoToLoginScreen()
        }
    }

    open fun clearLocalDataAndGoToLoginScreen() {}

    override fun showSimpleAlert(message: Any?, function: (() -> Unit)?) {
        with(getSafeString(message)) {
            Alerts.showDialogOk(
                this@BaseActivity,
                getString(R.string.aviso),
                this,
                onOk = { function?.invoke() })
        }
    }

    override fun showMessage(message: Any?, duration: Int) {
        with(getSafeString(message)) {
            getSnackBar(message = this, duration = duration).show()
        }
    }

    override fun toast(message: Any?, duration: Int?) {
        with(getSafeString(message)) {
            toLogcat()
            Toast.makeText(this@BaseActivity, this, duration ?: Toast.LENGTH_SHORT).show()
        }
    }

    override fun finish() {
        super.finish()
    }

}