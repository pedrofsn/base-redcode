package br.com.redcode.easyrestful.library.domain

import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.fragment.BaseFragmentMVVM

abstract class BaseFragmentMVVMRestful<VM : AbstractBaseViewModel> : BaseFragmentMVVM<VM>() {

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
