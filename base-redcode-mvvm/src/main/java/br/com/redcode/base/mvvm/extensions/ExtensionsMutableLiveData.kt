package br.com.redcode.base.mvvm.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.isEmpty() = value == null