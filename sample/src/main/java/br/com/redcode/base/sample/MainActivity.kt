package br.com.redcode.base.sample

import android.view.View
import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.base.extensions.goTo
import br.com.redcode.base.sample.contact.ContactActivity

class MainActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_main

    override fun afterOnCreate() {

    }

    fun click(view: View?) = goTo<ContactActivity>()
    fun openGithub(view: View?) = goTo<MyGithub>()

}
