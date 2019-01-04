package br.com.redcode.base.mvvm.extensions

import br.com.redcode.base.utils.Constants.INVALID_VALUE

fun Long.isValid() = INVALID_VALUE.toLong() != this