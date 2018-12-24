package br.com.redcode.easyrestful.library.impl.viewmodel

import br.com.redcode.easyrestful.library.domain.BaseViewModelRestful
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : BaseViewModelRestful(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

}