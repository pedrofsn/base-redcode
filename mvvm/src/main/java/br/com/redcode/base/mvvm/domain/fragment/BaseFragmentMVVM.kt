package br.com.redcode.base.mvvm.domain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.redcode.base.fragments.BaseFragment
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.domain.MVVM
import br.com.redcode.base.mvvm.models.EventMessage


abstract class BaseFragmentMVVM<VM : AbstractBaseViewModel> : BaseFragment(), MVVM<VM> {

    override lateinit var viewModel: VM
    abstract override val classViewModel: Class<VM>

    override val observerProcessing by lazy { initObserverProcessing() }
    override val observerEvents by lazy { initObserverEvents() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity!!).get(classViewModel)
        return super.onCreateView(inflater, container, savedInstanceState)
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