package br.com.redcode.base.interfaces

import com.google.android.material.snackbar.Snackbar


/**
 * Created by pedrofsn on 25/02/18.
 */
interface Alertable {

    fun showSimpleAlert(message: Any?, function: (() -> Unit)? = null)
    fun showMessage(message: Any?, duration: Int = Snackbar.LENGTH_SHORT)
    fun toast(message: Any?, duration: Int? = null)
    fun finish()

}