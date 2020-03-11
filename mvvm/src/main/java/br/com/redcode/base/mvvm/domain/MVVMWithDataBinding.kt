package br.com.redcode.base.mvvm.domain

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

interface MVVMWithDataBinding<B : ViewDataBinding, VM : AbstractBaseViewModel> : MVVM<VM> {

    var binding: B
    val idBRViewModel: Int

    fun defineMVVM(lifecycleOwner: LifecycleOwner) {
        binding.setVariable(idBRViewModel, viewModel)
        binding.lifecycleOwner = lifecycleOwner
    }

}