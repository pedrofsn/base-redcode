package br.com.redcode.base.mvvm.databinding.domain


import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import br.com.redcode.base.mvvm.databinding.MVVMWithDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.activity.BaseActivityMVVM

abstract class BaseActivityMVVMDataBinding<B : ViewDataBinding, VM : AbstractBaseViewModel> :
    BaseActivityMVVM<VM>(),
    MVVMWithDataBinding<B, VM> {

    override lateinit var binding: B
    abstract override val idBRViewModel: Int

    override fun setupLayout() {
        binding = DataBindingUtil.setContentView(this, layout)
        viewModel = ViewModelProvider(this).get(classViewModel)
        defineMVVM(this)
        setupUI()

        observeEvents()
        lifecycle.addObserver(viewModel)
    }

}