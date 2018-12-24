package br.com.redcode.base.extensions

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 * Created by pedrofsn on 17/10/2017.
 */

fun EditText.getString(): String {

    // HIDE KEYBOARD
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

    return text.toString().trim()
}

fun EditText.clear() = setText("")

fun EditText.handleEnterKeyboard(function: (EditText) -> Unit) = setOnEditorActionListener { _, actionId, _ ->
    if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
        function(this)
    }
    return@setOnEditorActionListener true
}