package br.com.redcode.base.sample

import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.base.sample.contact.ContactActivity

class MainActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_main

    override fun afterOnCreate() {
        goTo<ContactActivity>()
    }
}
