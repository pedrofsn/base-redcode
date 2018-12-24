package br.com.redcode.easyrestful.library.domain


import androidx.databinding.ViewDataBinding
import br.com.redcode.base.utils.Alerts
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.activity.BaseActivityMVVM
import br.com.redcode.base.mvvm.extensions.isValid
import br.com.redcode.easyreftrofit.library.CallbackNetworkRequest
import br.com.redcode.easyreftrofit.library.model.ErrorHandled
import br.com.redcode.easyrestful.library.R

abstract class BaseActivityMVVMRestful<B : ViewDataBinding, VM : AbstractBaseViewModel> : BaseActivityMVVM<B, VM>(),
    CallbackNetworkRequest {

    override fun handleEvent(event: String, obj: Any?) {
        val string = if (obj != null && obj is String) obj else null

        when (event) {
            "onNetworkHttpError" -> if (obj != null && obj is ErrorHandled) onNetworkHttpError(obj)
            "onNetworkUnknownError" -> string?.let { onNetworkUnknownError(it) }
            "onNetworkTimeout" -> onNetworkTimeout()
            "onNetworkError" -> onNetworkError()
            else -> super.handleEvent(event, obj)
        }
    }

    // CTRL+C AND CTRL+V FROM BaseActivityRestful - START

    override fun onNetworkHttpError(errorHandled: ErrorHandled) {
        errorHandled.apply {
            hideProgress()
            val callback = {
                if (actionAPI.isValid()) {
                    handleActionAPI(actionAPI, id)
                }
            }

            if (message.isBlank()) {
                callback.invoke()
            } else {
                Alerts.showDialogOk(
                        context = this@BaseActivityMVVMRestful,
                        mensagem = message,
                        onOk = callback
                )
            }
        }
    }

    override fun onNetworkUnknownError(message: String) {
        hideProgress()
        Alerts.showDialogOk(this, getString(R.string.erro), message)
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

    // CTRL+C AND CTRL+V FROM BaseActivityRestful - END

}