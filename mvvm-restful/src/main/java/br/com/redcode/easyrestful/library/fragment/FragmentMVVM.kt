package br.com.redcode.easyrestful.library.fragment

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.easyrestful.library.BR
import br.com.redcode.easyrestful.library.domain.BaseFragmentMVVMRestful

abstract class FragmentMVVM<B : ViewDataBinding, VM : AbstractBaseViewModel>(override val idBRViewModel: Int = BR.viewModel) :
    BaseFragmentMVVMRestful<B, VM>()