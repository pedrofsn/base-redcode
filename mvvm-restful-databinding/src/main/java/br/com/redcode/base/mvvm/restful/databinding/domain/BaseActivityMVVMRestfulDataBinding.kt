package br.com.redcode.base.mvvm.restful.databinding.domain


import android.content.Context
import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.databinding.domain.BaseActivityMVVMDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.easyrestful.library.domain.Networkable

abstract class BaseActivityMVVMRestfulDataBinding<B : ViewDataBinding, VM : AbstractBaseViewModel> :
    BaseActivityMVVMDataBinding<B, VM>(), Networkable {

    override val context: Context by lazy { this@BaseActivityMVVMRestfulDataBinding }

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