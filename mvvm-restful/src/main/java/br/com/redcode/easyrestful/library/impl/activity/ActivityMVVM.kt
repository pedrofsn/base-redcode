package br.com.redcode.easyrestful.library.impl.activity

import br.com.redcode.base.utils.Constants.ERROR_API_CLEAN_AND_FORCE_LOGIN
import br.com.redcode.base.utils.Constants.ERROR_API_TOKEN_EXPIRATION
import br.com.redcode.easyrestful.library.domain.BaseActivityMVVMRestful
import br.com.redcode.easyrestful.library.impl.viewmodel.BaseViewModel

abstract class ActivityMVVM<VM : BaseViewModel> : BaseActivityMVVMRestful<VM>() {

    override fun handleActionAPI(action: Int, id: String) {
        when (action) {
            ERROR_API_CLEAN_AND_FORCE_LOGIN -> clearLocalDataAndGoToLoginScreen()
            ERROR_API_TOKEN_EXPIRATION -> recreateToken()
            else -> super.handleActionAPI(action, id)
        }
    }

    open fun recreateToken() {
        // DO YOUR LOGIC
    }

}