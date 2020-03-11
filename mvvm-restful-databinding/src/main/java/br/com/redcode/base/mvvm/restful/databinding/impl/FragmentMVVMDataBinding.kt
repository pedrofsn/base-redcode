package br.com.redcode.base.mvvm.restful.databinding.impl

import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.databinding.domain.BaseFragmentMVVMDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.restful.databinding.BR

abstract class FragmentMVVMDataBinding<B : ViewDataBinding, VM : AbstractBaseViewModel>(override val idBRViewModel: Int = BR.viewModel) :
    BaseFragmentMVVMDataBinding<B, VM>()