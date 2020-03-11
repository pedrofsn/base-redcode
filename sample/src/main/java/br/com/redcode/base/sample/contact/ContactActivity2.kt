package br.com.redcode.base.sample.contact

/*
    CREATED BY @PEDROFSN
*/

import android.widget.Toast
import br.com.redcode.base.mvvm.extensions.observer
import br.com.redcode.base.sample.R
import br.com.redcode.easyrestful.library.impl.activity.ActivityMVVM
import kotlinx.android.synthetic.main.activity_contact_2.*

class ContactActivity2 : ActivityMVVM<ContactViewModel>() {

    override val classViewModel = ContactViewModel::class.java
    override val layout = R.layout.activity_contact_2

    private val mObserver = observer<String> { setupText(it) }

    override fun observeEvents() {
        super.observeEvents()
        viewModel.liveData.observe(this, mObserver)
    }

    override fun afterOnCreate() {
        enableHomeAsUpActionBar()
        viewModel.load()
    }

    private fun setupText(email: String) {
        textView.text = email
        toast("Com ViewModel e sem ViewDataBinding!", Toast.LENGTH_LONG)
    }

}