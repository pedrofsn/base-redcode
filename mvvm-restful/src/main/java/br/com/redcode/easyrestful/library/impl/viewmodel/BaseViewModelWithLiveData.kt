package br.com.redcode.easyrestful.library.impl.viewmodel

import androidx.lifecycle.MutableLiveData
import br.com.redcode.base.utils.Constants.INVALID_VALUE

abstract class BaseViewModelWithLiveData<Model> : BaseViewModel() {

    val liveData = MutableLiveData<Model>()
    var id: Long = INVALID_VALUE.toLong()

    abstract fun load()

}