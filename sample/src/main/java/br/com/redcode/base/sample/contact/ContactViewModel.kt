package br.com.redcode.base.sample.contact

import br.com.redcode.easyrestful.library.impl.viewmodel.BaseViewModelWithLiveData

class ContactViewModel : BaseViewModelWithLiveData<LabelContact>() {

    override fun load() {
        liveData.postValue(LabelContact(email = "pedrokra@gmail.com"))
    }

}