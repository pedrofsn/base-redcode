package br.com.redcode.base.sample.contact

/*
    CREATED BY @PEDROFSN
*/

import br.com.redcode.base.mvvm.restful.databinding.impl.ActivityMVVMDataBinding
import br.com.redcode.base.sample.R
import br.com.redcode.base.sample.databinding.ActivityContactBinding

class ContactActivity : ActivityMVVMDataBinding<ActivityContactBinding, ContactViewModel>() {

    override val classViewModel = ContactViewModel::class.java
    override val layout = R.layout.activity_contact

    override fun afterOnCreate() {
        enableHomeAsUpActionBar()
        viewModel.load()
    }

}