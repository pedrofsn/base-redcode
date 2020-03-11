package br.com.redcode.base.mvvm.restful.databinding.impl

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.restful.databinding.BR
import br.com.redcode.base.mvvm.restful.databinding.domain.BaseFragmentMVVMRestfulDataBinding

abstract class FragmentMVVMDataBinding<B : ViewDataBinding, VM : AbstractBaseViewModel>(override val idBRViewModel: Int = BR.viewModel) :
    BaseFragmentMVVMRestfulDataBinding<B, VM>()