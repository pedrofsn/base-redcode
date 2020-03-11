package br.com.redcode.base.mvvm.restful.databinding.impl

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.restful.databinding.BR
import br.com.redcode.base.mvvm.restful.databinding.domain.BaseActivityMVVMRestfulDataBinding
import br.com.redcode.base.utils.Constants.ERROR_API_CLEAN_AND_FORCE_LOGIN
import br.com.redcode.base.utils.Constants.ERROR_API_TOKEN_EXPIRATION
import br.com.redcode.easyrestful.library.impl.viewmodel.BaseViewModel

abstract class ActivityMVVMDataBinding<B : ViewDataBinding, VM : BaseViewModel> :
    BaseActivityMVVMRestfulDataBinding<B, VM>() {

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