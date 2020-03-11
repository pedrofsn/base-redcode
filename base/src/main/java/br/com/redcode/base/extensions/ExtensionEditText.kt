package br.com.redcode.base.extensions

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 * Created by pedrofsn on 17/10/2017.
 */

fun EditText.getString(): String {

    // HIDE KEYBOARD
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

    return text.toString().trim()
}

fun EditText.clear() = setText("")

fun EditText.handleEnterKeyboard(function: (EditText) -> Unit) =
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
            function(this)
        }
        return@setOnEditorActionListener true
    }

fun EditText.blockEmojis(): InputFilter {
    class EmojiExcludeFilter : InputFilter {

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            for (i in start until end) {
                val type = Character.getType(source[i])
                if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    return ""
                }
            }
            return null
        }
    }

    val filter = EmojiExcludeFilter()
    filters = arrayOf<InputFilter>(filter)
    return filter
}