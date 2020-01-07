package br.com.redcode.base.sample.contact

import br.com.redcode.easyrestful.library.impl.viewmodel.BaseViewModelWithLiveData

class ContactViewModel : BaseViewModelWithLiveData<String>() {

    override fun load() = liveData.postValue("pedrokra@gmail.com")

}