package br.com.redcode.easyrestful.library

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.fragment.BaseFragmentMVVM
import br.com.redcode.easyreftrofit.library.model.ErrorHandled

abstract class BaseFragmentMVVMRestful<B : ViewDataBinding, VM : AbstractBaseViewModel> : BaseFragmentMVVM<B, VM>() {

    override fun handleEvent(event: String, obj: Any?) {
        when (event) {
            "onNetworkTimeout" -> (this as BaseActivityMVVMRestful<B, VM>).onNetworkTimeout()
            "onNetworkError" -> (this as BaseActivityMVVMRestful<B, VM>).onNetworkError()
            "onNetworkUnknownError" -> {
                if (obj != null && obj is String) {
                    (this as BaseActivityMVVMRestful<B, VM>).onNetworkUnknownError(obj)
                }
            }
            "onNetworkHttpError" -> {
                if (obj != null && obj is ErrorHandled) {
                    (this as BaseActivityMVVMRestful<B, VM>).onNetworkHttpError(obj)
                }
            }
            else -> super.handleEvent(event, obj)
        }
    }

}