package br.com.redcode.base.mvvm.databinding

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.MVVM

interface MVVMWithDataBinding<B : ViewDataBinding, VM : AbstractBaseViewModel> :
    MVVM<VM> {

    var binding: B
    val idBRViewModel: Int

    fun defineMVVM(lifecycleOwner: LifecycleOwner) {
        binding.setVariable(idBRViewModel, viewModel)
        binding.lifecycleOwner = lifecycleOwner
    }

}