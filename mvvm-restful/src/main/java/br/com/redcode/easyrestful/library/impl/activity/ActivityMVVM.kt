package br.com.redcode.easyrestful.library.impl.activity

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.utils.Constants.ERROR_API_CLEAN_AND_FORCE_LOGIN
import br.com.redcode.base.utils.Constants.ERROR_API_TOKEN_EXPIRATION
import br.com.redcode.easyrestful.library.BR
import br.com.redcode.easyrestful.library.domain.BaseActivityMVVMRestful
import br.com.redcode.easyrestful.library.impl.viewmodel.BaseViewModel

abstract class ActivityMVVM<B : ViewDataBinding, VM : BaseViewModel> : BaseActivityMVVMRestful<B, VM>() {

    override val idBRViewModel: Int = BR.viewModel

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