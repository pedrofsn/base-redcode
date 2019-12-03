package br.com.redcode.easyrestful.library.domain

import android.content.Context
import androidx.annotation.StringRes
import br.com.redcode.base.interfaces.Progressable
import br.com.redcode.base.mvvm.extensions.isValid
import br.com.redcode.base.utils.Alerts
import br.com.redcode.easyreftrofit.library.CallbackNetworkRequest
import br.com.redcode.easyreftrofit.library.model.ErrorHandled
import br.com.redcode.easyrestful.library.R

interface Networkable : CallbackNetworkRequest,
    Progressable {

    val context: Context

    fun onNetworkUnknownError(obj: Any?) {
        if (obj != null && obj is String) {
            onNetworkUnknownError(obj)
        }
    }

    override fun onNetworkUnknownError(message: String) {
        hideProgress()
        Alerts.showDialogOk(
            context,
            getString(R.string.erro),
            message
        )
    }

    override fun onNetworkTimeout() {
        hideProgress()
        Alerts.showDialogOk(
            context,
            getString(R.string.erro),
            getString(R.string.o_servidor_demorou_a_responder)
        )
    }

    override fun onNetworkError() {
        hideProgress()
        Alerts.showDialogOk(
            context,
            getString(R.string.erro),
            getString(R.string.error_conectivity)
        )
    }

    fun onNetworkHttpError(obj: Any?) {
        if (obj != null && obj is ErrorHandled) {
            onNetworkHttpError(obj)
        }
    }

    override fun onNetworkHttpError(errorHandled: ErrorHandled) {
        errorHandled.apply {
            hideProgress()
            val callback = {
                if (actionAPI.isValid()) {
                    handleActionAPI(actionAPI, id)
                }
            }

            when {
                message.isBlank() -> callback.invoke()
                else -> Alerts.showDialogOk(
                    context = context,
                    mensagem = message,
                    onOk = callback
                )
            }
        }
    }

    fun handleActionAPI(action: Int, id: String)

    private fun getString(@StringRes resId: Int) = context.getString(resId)

}