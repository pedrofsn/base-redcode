package br.com.redcode.base.sample

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.annotation.NonNull
import br.com.redcode.base.activities.BaseActivityWebView
import vcm.github.webkit.proview.ProWebView

class MyGithub : BaseActivityWebView() {

    override val layout: Int = R.layout.activity_webview
    override val webView by lazy { findViewById<ProWebView>(R.id.proWebView) }
    override val myUrl: String = "https://github.com/pedrofsn"

    override fun handleWebView() {
        webView.setActivity(this)
        webView.setProClient(object : ProWebView.ProClient() {
            override fun onProgressChanged(webView: ProWebView?, progress: Int) {
                super.onProgressChanged(webView, progress)

                if (progress == 100) {
                    onLoadedPage()
                    hideProgress()
                } else {
                    showProgress()
                }
            }

            override fun onInformationReceived(
                webView: ProWebView?,
                url: String?,
                title: String?,
                favicon: Bitmap?
            ) {
                super.onInformationReceived(webView, url, title, favicon)
                url?.let { customUrl -> processingURL(customUrl) }
            }
        })
    }

    private fun onLoadedPage() = showMessage("Page Loaded")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        webView.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        webView.onRequestPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onSaveInstanceState(outState: Bundle) {
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