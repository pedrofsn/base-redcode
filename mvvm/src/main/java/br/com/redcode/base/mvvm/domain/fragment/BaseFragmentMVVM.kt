package br.com.redcode.base.mvvm.domain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import br.com.redcode.base.fragments.BaseFragment
import br.com.redcode.base.mvvm.domain.AbstractBaseViewModel
import br.com.redcode.base.mvvm.extensions.observer
import br.com.redcode.base.mvvm.models.Event
import br.com.redcode.base.mvvm.models.EventMessage

abstract class BaseFragmentMVVM<B : ViewDataBinding, VM : AbstractBaseViewModel> : BaseFragment() {

    protected lateinit var binding: B
    lateinit var viewModel: VM
    abstract val classViewModel: Class<VM>
    abstract val idBRViewModel: Int

    private val observerEvents =
            observer<Event<EventMessage>> { it -> it.getContentIfNotHandled()?.let { obj -> handleEvent(obj) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        viewModel = ViewModelProviders.of(activity!!).get(classViewModel)
        binding.setVariable(idBRViewModel, viewModel)
        binding.setLifecycleOwner(this as LifecycleOwner)
        return binding.root
    }

    override fun setupUI() {
        if (activity != null) {
            (viewModel as AbstractBaseViewModel).events.observe(this, observerEvents)
        }
    }

    private fun handleEvent(eventMessage: EventMessage) = handleEvent(eventMessage.event, eventMessage.obj)

    fun handleEvent(event: String) = handleEvent(event, null)

    open fun handleEvent(event: String, obj: Any? = null) {
        when (event) {
            "showSimpleAlert" -> {
                if (obj != null && obj is String) {
                    showSimpleAlert(obj)
                }
            }
            "showMessage" -> {
                if (obj != null && obj is String) {
                    showMessage(obj)
                }
            }
            else -> throw RuntimeException("Event not handled in 'handleEvent' method: $event")
        }
    }
}