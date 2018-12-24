package br.com.redcode.base.mvvm.domain

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.redcode.base.mvvm.extensions.isEmpty
import br.com.redcode.base.mvvm.models.Event
import br.com.redcode.base.mvvm.models.EventMessage

abstract class AbstractBaseViewModel : ViewModel(), LifecycleObserver {

    val events = MutableLiveData<Event<EventMessage>>()

    fun sendEventToUI(event: String) = events.postValue(Event(EventMessage(event)))
    fun sendEventToUI(event: String, obj: Any? = null) = events.postValue(Event(EventMessage(event, obj)))

    fun showSimpleAlert(message: String) = sendEventToUI("showSimpleAlert", message)
    fun showProgressbar(liveData: MutableLiveData<*>) = showProgressbar(liveData.isEmpty())
    fun showProgressbar(show: Boolean = true) = sendEventToUI(if (show) "showProgressbar" else "hideProgressbar")

}