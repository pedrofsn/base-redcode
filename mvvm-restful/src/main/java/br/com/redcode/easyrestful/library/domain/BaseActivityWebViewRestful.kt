package br.com.redcode.easyrestful.library.domain

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.annotation.NonNull
import br.com.redcode.base.activities.BaseActivityWebView
import br.com.redcode.base.utils.Alerts
import br.com.redcode.base.utils.Constants
import br.com.redcode.easyreftrofit.library.CallbackNetworkRequest
import br.com.redcode.easyreftrofit.library.model.ErrorHandled
import br.com.redcode.easyrestful.library.R
import vcm.github.webkit.proview.ProWebView

abstract class BaseActivityWebViewRestful : BaseActivityWebView(), CallbackNetworkRequest {

    override val layout: Int = R.layout.activity_webview
    override val webView by lazy { findViewById<ProWebView>(R.id.proWebView) }

    // IF HAS SSL, PROBABLY NEED TO BE FALSE
    var shouldOverrideUrlLoading = true

    override fun handleWebView() {
        val proClient = object : ProWebView.ProClient() {
            override fun onProgressChanged(webView: ProWebView?, progress: Int) {
                super.onProgressChanged(webView, progress)

                if (progress == 100) {
                    hideProgress()
                } else {
                    showProgress()
                }
            }

            override fun onInformationReceived(webView: ProWebView?, url: String?, title: String?, favicon: Bitmap?) {
                super.onInformationReceived(webView, url, title, favicon)
                url?.let { customUrl -> processingURL(customUrl) }
            }

            override fun shouldOverrideUrlLoading(webView: ProWebView?, url: String?): Boolean {
                return when {
                    shouldOverrideUrlLoading -> super.shouldOverrideUrlLoading(webView, url)
                    else -> false
                }
            }
        }

        webView.setActivity(this)
        webView.setProClient(proClient)
    }

    override fun onNetworkHttpError(errorHandled: ErrorHandled) {
        errorHandled.apply {
            hideProgress()
            val callback = {
                if (Constants.INVALID_VALUE != actionAPI) {
                    handleActionAPI(actionAPI, id)
                }
            }

            if (message.isBlank()) {
                callback.invoke()
            } else {
                Alerts.showDialogOk(
                    context = this@BaseActivityWebViewRestful,
                    mensagem = message,
                    onOk = callback
                )
            }
        }
    }

    override fun onNetworkTimeout() {
        hideProgress()
        Alerts.showDialogOk(this, getString(R.string.erro), getString(R.string.o_servidor_demorou_a_responder))
    }

    override fun onNetworkError() {
        hideProgress()
        Alerts.showDialogOk(
            this,
            getString(R.string.erro),
            getString(R.string.error_conectivity)
        )
    }

    override fun onNetworkUnknownError(message: String) {
        hideProgress()
        Alerts.showDialogOk(this, getString(R.string.erro), message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        webView.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        webView.onRequestPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        webView.onSavedInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.onDestroy()
    }

    override fun loadHtml(html: String) = webView.loadHtml(html)
    override fun loadUrl(url: String) = webView.loadUrl(url)

}