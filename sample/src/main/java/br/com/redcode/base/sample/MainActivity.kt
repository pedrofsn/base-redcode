package br.com.redcode.base.sample

import br.com.redcode.base.activities.BaseActivity

class MainActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_main

    override fun afterOnCreate() {
        goTo<MyGithub>()
    }
}
