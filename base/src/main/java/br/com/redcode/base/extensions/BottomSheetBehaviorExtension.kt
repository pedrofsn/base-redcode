package br.com.redcode.base.extensions

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*

/*
    CREATED BY @PEDROFSN
*/

fun BottomSheetBehavior<*>.isNotHidden() = STATE_HIDDEN != state

fun BottomSheetBehavior<*>.hide() {
    state = STATE_HIDDEN
}

fun BottomSheetBehavior<*>.openOrCloseBottomSheet() {
    state = when (state) {
        STATE_COLLAPSED -> STATE_EXPANDED
        STATE_HIDDEN -> STATE_EXPANDED
        else -> STATE_HIDDEN
    }
}