package br.com.redcode.base.mvvm.domain

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import br.com.redcode.base.interfaces.Alertable
import br.com.redcode.base.interfaces.Progressable
import br.com.redcode.base.mvvm.extensions.observer
import br.com.redcode.base.mvvm.models.Event
import br.com.redcode.base.mvvm.models.EventMessage

interface MVVM<VM : AbstractBaseViewModel> : Alertable, Progressable {

    var viewModel: VM
    val classViewModel: Class<VM>

    val observerProcessing: Observer<Boolean>
    val observerEvents: Observer<Event<EventMessage>>

    fun initObserverProcessing(): Observer<Boolean> {
        return observer { processing = it }
    }

    fun initObserverEvents(): Observer<Event<EventMessage>> {
        return observer<Event<EventMessage>> {
            it.getContentIfNotHandled()?.let { obj ->
                handleEvent(obj)
            }
        }
    }

    fun startObservers() {
        observeEvents()
        observeProcessing()
    }

    fun observeEvents() {
        (viewModel as AbstractBaseViewModel).events.observe(this as LifecycleOwner, observerEvents)
    }

    fun observeProcessing() {
        (viewModel as? AbstractBaseViewModel)?.processing?.observe(
            this as LifecycleOwner,
            observerProcessing
        )
    }

    fun handleEvent(event: String)

    fun handleEvent(eventMessage: EventMessage)

    fun handleEvent(event: String, obj: Any?) {
        when (event) {
            "showSimpleAlert" -> showSimpleAlert(obj)
            "toast" -> toast(obj)
            "showMessage" -> showMessage(obj)
            "showProgressbar" -> showProgress()
            "hideProgressbar" -> hideProgress()
            else -> throw RuntimeException("Event not handled in 'handleEvent' method: $event")
        }
    }

    override fun showProgress() {
        viewModel.processing.postValue(true)
    }

    override fun hideProgress() {
        viewModel.processing.postValue(false)
    }

}