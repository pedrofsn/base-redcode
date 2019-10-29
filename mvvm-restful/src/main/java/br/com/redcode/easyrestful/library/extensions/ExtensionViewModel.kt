package br.com.redcode.easyrestful.library.extensions

import androidx.annotation.StringRes
import br.com.redcode.base.extensions.getSafeMessage
import br.com.redcode.base.extensions.isWithoutErrorInformation
import br.com.redcode.base.models.ErrorAPI
import br.com.redcode.base.utils.Constants.ONE_SECOND_IN_MILLISECONDS
import br.com.redcode.easyrestful.library.impl.viewmodel.BaseViewModel
import br.com.redcode.easyrestful.library.impl.viewmodel.BaseViewModelWithLiveData
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
    CREATED BY @PEDROFSN
*/

fun BaseViewModel.onSuccess() = { e: ErrorAPI ->
    onSuccess(e)
}

fun BaseViewModel.onSuccess(e: ErrorAPI?) {
    e?.let {
        showProgressbar(false)
        if (e.isWithoutErrorInformation()) {
            sendEventToUI(event = "onSuccess", obj = e.msg)
        }
    }
}

fun BaseViewModel.process(event: String? = null, function: (suspend () -> ErrorAPI?)?) {
    if (function != null) {
        showProgressbar()
        launch(io()) {
            val result = function.invoke()
            if (result?.isWithoutErrorInformation() == true) {
                val mEvent = event ?: "onSuccess"
                val obj = result.getSafeMessage()
                sendEventToUI(event = mEvent, obj = obj)
            }
        }
    }
}

inline fun BaseViewModel.processWithAlert(crossinline function: suspend () -> ErrorAPI?) {
    showProgressbar()
    launch(main()) {
        val result = async(io()) { function.invoke() }.await()
        showSimpleAlert(result)
        delay(ONE_SECOND_IN_MILLISECONDS)
        showProgressbar(false)
    }
}

fun <T> BaseViewModelWithLiveData<T>.process(
        showProgressBarOnce: Boolean = false,
        function: suspend () -> T?
) = process(
        function = function,
        showProgressBarOnce = showProgressBarOnce,
        onResult = null
)

fun <T> BaseViewModelWithLiveData<T>.process(
        showProgressBarOnce: Boolean = false,
        function: suspend () -> T?,
        onResult: ((T?) -> Unit)?
) {
    when {
        showProgressBarOnce -> showProgressbar(liveData)
        else -> showProgressbar()
    }

    launch(io()) {
        val result = function.invoke()
        liveData.postValue(result)
        onResult?.invoke(result)

        when (result) {
            null -> {
                delay(3)
                showProgressbar(false)
            }
            else -> showProgressbar(false)
        }
    }
}

inline fun BaseViewModel.showProgressbar(crossinline function: () -> Unit) {
    showProgressbar()
    function.invoke()
}

fun <T> BaseViewModelWithLiveData<T>.load(id: Long) {
    this.id = id
    load()
}

fun BaseViewModel.showSimpleAlert(@StringRes idString: Int) {
    launch(main()) {
        sendEventToUI("showSimpleAlert", idString)
    }
}

fun BaseViewModel.showSimpleAlert(e: ErrorAPI?) {
    e?.msg?.let {
        if (it.isNotBlank()) {
            showSimpleAlert(it)
        }
    }
}
