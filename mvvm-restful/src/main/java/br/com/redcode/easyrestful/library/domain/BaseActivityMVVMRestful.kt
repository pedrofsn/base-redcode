package br.com.redcode.easyrestful.library.domain


import android.content.Context
import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.activity.BaseActivityMVVM
import br.com.redcode.easyreftrofit.library.model.ErrorHandled

abstract class BaseActivityMVVMRestful<B : ViewDataBinding, VM : AbstractBaseViewModel> :
    BaseActivityMVVM<B, VM>(), Networkable {

    override val context: Context by lazy { this@BaseActivityMVVMRestful }

    override fun handleEvent(event: String, obj: Any?) = runOnUiThread {
        when (event) {
            "onNetworkHttpError" -> {
                if (obj != null && obj is ErrorHandled) {
                    onNetworkHttpError(obj)
                }
            }
            "onNetworkUnknownError" -> {
                if (obj != null && obj is String) {
                    onNetworkUnknownError(obj)
                }
            }
            "onNetworkTimeout" -> onNetworkTimeout()
            "onNetworkError" -> onNetworkError()
            else -> super.handleEvent(event, obj)
        }
    }

    override fun handleActionAPI(action: Int, id: String) {
        super.handleActionAPI(action, id)
    }

}