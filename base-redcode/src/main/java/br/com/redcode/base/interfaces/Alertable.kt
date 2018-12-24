package br.com.redcode.base.interfaces

import com.google.android.material.snackbar.Snackbar


/**
 * Created by pedrofsn on 25/02/18.
 */
interface Alertable {

    fun showSimpleAlert(message: String, function: (() -> Unit)? = null)
    fun showMessage(message: String, duration: Int = Snackbar.LENGTH_SHORT)
    fun toast(message: String?, duration: Int? = null)
    fun finish()

}