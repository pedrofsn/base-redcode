package br.com.redcode.base.mvvm.domain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import br.com.redcode.base.fragments.BaseFragment
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.MVVM
import br.com.redcode.base.mvvm.models.EventMessage


abstract class BaseFragmentMVVM<B : ViewDataBinding, VM : AbstractBaseViewModel> : BaseFragment(),
    MVVM<B, VM> {

    override lateinit var binding: B
    override lateinit var viewModel: VM
    abstract override val classViewModel: Class<VM>
    abstract override val idBRViewModel: Int

    override val observerProcessing by lazy { initObserverProcessing() }
    override val observerEvents by lazy { initObserverEvents() }

    override var processing: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        viewModel = ViewModelProviders.of(activity!!).get(classViewModel)
        defineMVVM(this)
        return binding.root
    }

    override fun setupUI() {
        if (activity != null) {
            startObservers()
        }
    }

    override fun handleEvent(eventMessage: EventMessage) {
        activity?.runOnUiThread {
            handleEvent(eventMessage.event, eventMessage.obj)
        }
    }

    override fun handleEvent(event: String) {
        activity?.runOnUiThread {
            handleEvent(event, null)
        }
    }

    override fun showProgress() {
        super<MVVM>.showProgress()
        super<BaseFragment>.showProgress()
    }

    override fun hideProgress() {
        super<MVVM>.hideProgress()
        super<BaseFragment>.hideProgress()
    }
}