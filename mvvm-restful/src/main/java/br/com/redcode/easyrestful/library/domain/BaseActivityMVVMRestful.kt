package br.com.redcode.easyrestful.library.domain


import android.content.Context
import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.activity.BaseActivityMVVM

abstract class BaseActivityMVVMRestful<B : ViewDataBinding, VM : AbstractBaseViewModel> :
    BaseActivityMVVM<B, VM>(), Networkable {

    override val context: Context by lazy { this@BaseActivityMVVMRestful }

    override fun handleEvent(event: String, obj: Any?) = runOnUiThread {
        when (event) {
            "onNetworkTimeout" -> onNetworkTimeout()
            "onNetworkError" -> onNetworkError()
            "onNetworkUnknownError" -> onNetworkUnknownError(obj)
            "onNetworkHttpError" -> onNetworkHttpError(obj)
            else -> super.handleEvent(event, obj)
        }
    }

}