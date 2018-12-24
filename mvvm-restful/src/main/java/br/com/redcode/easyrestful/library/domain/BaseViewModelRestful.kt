package br.com.redcode.easyrestful.library.domain

import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.easyreftrofit.library.CallbackNetworkRequest
import br.com.redcode.easyreftrofit.library.model.ErrorHandled

abstract class BaseViewModelRestful : AbstractBaseViewModel(), CallbackNetworkRequest {

    override fun onNetworkHttpError(errorHandled: ErrorHandled) = sendEventToUI("onNetworkHttpError", errorHandled)
    override fun onNetworkTimeout() = sendEventToUI("onNetworkTimeout")
    override fun onNetworkError() = sendEventToUI("onNetworkError")
    override fun onNetworkUnknownError(message: String) = sendEventToUI("onNetworkUnknownError", message)

}