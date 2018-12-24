package br.com.redcode.easyrestful.library

import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.base.utils.Alerts
import br.com.redcode.base.utils.Constants
import br.com.redcode.easyreftrofit.library.CallbackNetworkRequest
import br.com.redcode.easyreftrofit.library.model.ErrorHandled

abstract class BaseActivityRestful : BaseActivity(), CallbackNetworkRequest {

    // CTRL+C AND CTRL+V FROM BaseActivityMVVMRestful - START

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
                        context = this@BaseActivityRestful,
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

    // CTRL+C AND CTRL+V FROM BaseActivityMVVMRestful - END

}