package br.com.redcode.base.mvvm.databinding.domain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import br.com.redcode.base.mvvm.databinding.MVVMWithDataBinding
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.fragment.BaseFragmentMVVM


abstract class BaseFragmentMVVMDataBinding<B : ViewDataBinding, VM : AbstractBaseViewModel> :
    BaseFragmentMVVM<VM>(), MVVMWithDataBinding<B, VM> {

    override lateinit var binding: B
    abstract override val idBRViewModel: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        viewModel = ViewModelProvider(activity!!).get(classViewModel)
        defineMVVM(this)
        return binding.root
    }

}