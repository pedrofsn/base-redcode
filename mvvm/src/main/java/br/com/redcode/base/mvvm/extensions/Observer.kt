package br.com.redcode.base.mvvm.extensions

import androidx.lifecycle.Observer

inline fun <T> observer(crossinline code: (result: T) -> Unit): Observer<T> =
    Observer { obj: T? -> obj?.let { code(it) } }