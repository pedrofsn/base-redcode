package br.com.redcode.base.mvvm.extensions

import androidx.databinding.BaseObservable
import kotlin.properties.Delegates


fun BaseObservable.watchBR(fieldId: Int) = Delegates.observable("") { _, _, _ -> notifyPropertyChanged(fieldId) }
fun <T> BaseObservable.watchBR(fieldId: Int, defaultValue: T) =
        Delegates.observable(defaultValue) { _, _, _ -> notifyPropertyChanged(fieldId) }

fun BaseObservable.watchBoolean(fieldId: Int, defaultValue: Boolean = false) =
        Delegates.observable(defaultValue) { _, _, _ -> notifyPropertyChanged(fieldId) }
