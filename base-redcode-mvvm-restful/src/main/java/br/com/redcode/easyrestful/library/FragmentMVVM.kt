package br.com.redcode.easyrestful.library

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel

abstract class FragmentMVVM<B : ViewDataBinding, VM : AbstractBaseViewModel>(override val idBRViewModel: Int = BR.viewModel) :
    BaseFragmentMVVMRestful<B, VM>()