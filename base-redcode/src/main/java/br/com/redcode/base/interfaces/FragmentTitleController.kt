package br.com.redcode.base.interfaces

import br.com.redcode.base.fragments.BaseFragment

/**
 * Created by pedrofsn on 07/02/18.
 */
interface FragmentTitleController {

    fun addFragment(fragment: BaseFragment, title: String)

}