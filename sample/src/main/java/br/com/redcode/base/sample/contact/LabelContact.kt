package br.com.redcode.base.sample.contact

import androidx.databinding.BaseObservable

data class LabelContact(
    val myList: List<String>
) : BaseObservable() {

//    @get:Bindable
//    var email: String by watchBR(BR.email)

}