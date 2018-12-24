package br.com.redcode.base.extensions

import androidx.appcompat.widget.SwitchCompat


fun SwitchCompat.inverse() {
    isChecked = isChecked.not()
}