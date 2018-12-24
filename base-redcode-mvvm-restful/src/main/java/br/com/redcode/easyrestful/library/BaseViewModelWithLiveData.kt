package br.com.redcode.easyrestful.library

import androidx.lifecycle.MutableLiveData
import br.com.redcode.base.utils.Constants.INVALID_VALUE

abstract class BaseViewModelWithLiveData<Model> : BaseViewModel() {

    val liveData = MutableLiveData<Model>()
    var id = INVALID_VALUE

    abstract fun load()

}