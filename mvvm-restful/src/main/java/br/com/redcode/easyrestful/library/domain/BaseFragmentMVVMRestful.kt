package br.com.redcode.easyrestful.library.domain

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.fragment.BaseFragmentMVVM

abstract class BaseFragmentMVVMRestful<B : ViewDataBinding, VM : AbstractBaseViewModel> :
    BaseFragmentMVVM<B, VM>() {

    override fun handleEvent(event: String, obj: Any?) {
        activity?.runOnUiThread {
            val reference = activity as? Networkable

            when (event) {
                "onNetworkTimeout" -> reference?.onNetworkTimeout()
                "onNetworkError" -> reference?.onNetworkError()
                "onNetworkUnknownError" -> reference?.onNetworkUnknownError(obj)
                "onNetworkHttpError" -> reference?.onNetworkHttpError(obj)
                else -> super.handleEvent(event, obj)
            }
        }

    }
}
