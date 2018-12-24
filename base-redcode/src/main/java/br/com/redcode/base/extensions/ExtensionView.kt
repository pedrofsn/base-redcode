package br.com.redcode.base.extensions

import android.view.View
import android.widget.TextView

/**
 * Created by pedrofsn on 01/12/17.
 */
fun View.showOrHide(showOrHide: Boolean?) {
    showOrHide?.let { if (showOrHide) visible() else gone() }
}

fun View.invisible() = changeVisibility(this, View.INVISIBLE)

fun View.gone() = changeVisibility(this, View.GONE)

fun View.visible() = changeVisibility(this, View.VISIBLE)

fun View.isVisible() = visibility == View.VISIBLE

private fun changeVisibility(view: View, visibility: Int) {
    view.visibility = visibility
}

fun View.handleTime(textView: TextView) {
    val onClick = textView.handleTime()
    setOnClickListener { onClick.invoke() }
}

fun View.inverse() {
    isSelected = isSelected.not()
}