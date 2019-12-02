package br.com.redcode.base.interfaces

/**
 * Created by pedrofsn on 17/10/2017.
 */
interface Progressable {

    var processing: Boolean

    fun showProgress()
    fun hideProgress()

}