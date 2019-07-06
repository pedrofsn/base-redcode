package br.com.redcode.base.sample.contact

/*
    CREATED BY @PEDROFSN
*/

import br.com.redcode.base.sample.R
import br.com.redcode.base.sample.databinding.ActivityContactBinding
import br.com.redcode.easyrestful.library.impl.activity.ActivityMVVM

class ContactActivity : ActivityMVVM<ActivityContactBinding, ContactViewModel>() {

    override val classViewModel = ContactViewModel::class.java
    override val layout = R.layout.activity_contact

    override fun afterOnCreate() {
        enableHomeAsUpActionBar()
        viewModel.load()
    }
}