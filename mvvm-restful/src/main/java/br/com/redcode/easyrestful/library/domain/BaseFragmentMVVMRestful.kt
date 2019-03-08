package br.com.redcode.easyrestful.library.domain

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.fragment.BaseFragmentMVVM
import br.com.redcode.easyreftrofit.library.model.ErrorHandled
import br.com.redcode.easyrestful.library.domain.BaseActivityRestful

abstract class BaseFragmentMVVMRestful<B : ViewDataBinding, VM : AbstractBaseViewModel> : BaseFragmentMVVM<B, VM>() {

    override fun handleEvent(event: String, obj: Any?) {
        when (event) {
            "onNetworkTimeout" -> (activity as? BaseActivityRestful)?.onNetworkTimeout()
            "onNetworkError" -> (activity as? BaseActivityRestful)?.onNetworkError()
            "onNetworkUnknownError" -> {
                if (obj != null && obj is String) {
                    (activity as? BaseActivityRestful)?.onNetworkUnknownError(obj)
                }
            }
            "onNetworkHttpError" -> {
                if (obj != null && obj is ErrorHandled) {
                    (activity as? BaseActivityRestful)?.onNetworkHttpError(obj)
                }
            }
            else -> super.handleEvent(event, obj)
        }
    }

}
