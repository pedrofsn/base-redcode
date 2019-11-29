package br.com.redcode.easyrestful.library.domain

import android.content.Context
import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.easyreftrofit.library.model.ErrorHandled

abstract class BaseActivityRestful : BaseActivity(), Networkable {

    override val context: Context by lazy { this@BaseActivityRestful }

    override fun onNetworkHttpError(errorHandled: ErrorHandled) {
        runOnUiThread {
            super.onNetworkHttpError(errorHandled)
        }
    }

    override fun onNetworkUnknownError(message: String) {
        runOnUiThread {
            super.onNetworkUnknownError(message)
        }
    }

    override fun onNetworkTimeout() {
        runOnUiThread {
            super.onNetworkTimeout()
        }
    }

    override fun onNetworkError() {
        runOnUiThread {
            super.onNetworkError()
        }
    }

    override fun handleActionAPI(action: Int, id: String) {
        super.handleActionAPI(action, id)
    }

}