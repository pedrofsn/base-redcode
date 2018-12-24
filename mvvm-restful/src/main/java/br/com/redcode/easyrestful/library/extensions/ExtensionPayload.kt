package br.com.redcode.easyrestful.library.extensions

import br.com.redcode.base.extensions.extract
import br.com.redcode.base.rest.PayloadError
import br.com.redcode.easyreftrofit.library.model.ErrorHandled

fun PayloadError.toModel(networkError: Int) = ErrorHandled(
        message = extract safe msg,
        actionAPI = extract safe acao,
        networkError = networkError,
        id = extract safe id
)