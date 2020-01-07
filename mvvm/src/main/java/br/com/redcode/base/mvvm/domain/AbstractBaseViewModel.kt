package br.com.redcode.base.mvvm.domain

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.redcode.base.mvvm.extensions.isEmpty
import br.com.redcode.base.mvvm.models.Event
import br.com.redcode.base.mvvm.models.EventMessage

abstract class AbstractBaseViewModel : ViewModel(), LifecycleObserver {

    val processing = MutableLiveData<Boolean>()
    val events = MutableLiveData<Event<EventMessage>>()

    fun sendEventToUI(event: String) = events.postValue(Event(EventMessage(event)))
    fun sendEventToUI(event: String, obj: Any? = null) = events.postValue(Event(EventMessage(event, obj)))

    fun toast(message: String) = sendEventToUI("toast", message)
    fun toast(@StringRes message: Int) = sendEventToUI("toast", message)
    fun showMessage(message: String) = sendEventToUI("showMessage", message)
    fun showMessage(@StringRes message: Int) = sendEventToUI("showMessage", message)
    fun showSimpleAlert(@StringRes message: Int) = sendEventToUI("showSimpleAlert", message)
    fun showSimpleAlert(message: String) = sendEventToUI("showSimpleAlert", message)
    fun showProgressbar(liveData: MutableLiveData<*>) = showProgressbar(liveData.isEmpty())

    fun showProgressbar(show: Boolean = true) {
        processing.postValue(show)
        sendEventToUI(if (show) "showProgressbar" else "hideProgressbar")
    }

}