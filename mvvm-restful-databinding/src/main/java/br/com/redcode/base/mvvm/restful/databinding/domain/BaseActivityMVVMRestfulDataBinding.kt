package br.com.redcode.base.mvvm.restful.databinding.domain


import androidx.databinding.ViewDataBinding
import br.com.redcode.base.mvvm.databinding.domain.BaseActivityMVVMDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel

abstract class BaseActivityMVVMRestfulDataBinding<B : ViewDataBinding, VM : AbstractBaseViewModel> :
    BaseActivityMVVMDataBinding<B, VM>()