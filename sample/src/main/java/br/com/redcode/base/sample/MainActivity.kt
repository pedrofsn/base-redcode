package br.com.redcode.base.sample

import android.view.View
import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.base.extensions.goTo
import br.com.redcode.base.sample.contact.ContactActivity
import br.com.redcode.base.sample.contact.ContactActivity2

class MainActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_main

    override fun afterOnCreate() {

    }

    fun openMVVM(@Suppress("UNUSED_PARAMETER") view: View?) = goTo<ContactActivity2>()
    fun openMVVMDataBinding(@Suppress("UNUSED_PARAMETER") view: View?) = goTo<ContactActivity>()
    fun openGithub(@Suppress("UNUSED_PARAMETER") view: View?) = goTo<MyGithub>()

}
