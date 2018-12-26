package br.com.redcode.easyrestful.library.impl.viewmodel

import androidx.lifecycle.MutableLiveData
import br.com.redcode.base.utils.Constants

abstract class BaseViewModelWithLiveData<Model> : BaseViewModel() {

    val liveData = MutableLiveData<Model>()
    var id = Constants.INVALID_VALUE

    abstract fun load()

}