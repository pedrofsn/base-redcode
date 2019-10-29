package br.com.redcode.base.extensions

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import br.com.redcode.base.utils.Alerts
import java.util.*

/**
 * Created by pedrofsn on 30/11/2017.
 */

fun TextView.getString() = text.toString().trim()

fun TextView.setTextOrHide(string: String?, additionViewToHide: View? = null) {
    if (string == null || string.isNullOrBlank()) {
        gone()
        additionViewToHide?.gone()
    } else {
        visible()
        additionViewToHide?.visible()
        text = string
    }
}

fun TextView.handleDate(callback: ((String) -> Unit)? = null, isMinimalAge: Boolean? = false) {
    val today = Calendar.getInstance().zeroTime()

    val calendar = try {
        when (isMinimalAge) {
            true -> Calendar.getInstance().minimalAge()
            else -> text.toString().toCalendarWithZeroTime() ?: today
        }
    } catch (e: Exception) {
        today
    }

    val callbackBirthday = configDatePickerListener(calendar, callback)
    val result = calendar.to__dd_MM_yyyy()

    text = result
    callback?.let { result }

    setOnClickListener {
        Alerts.showDatePicker(context, calendar, callbackBirthday)
    }
}

fun TextView.handleTime(): () -> Unit {
    var calendar = text.toString().toTime24h()

    if (calendar == null) calendar = Calendar.getInstance()

    val callback = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        calendar!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        text = calendar.to__HH_mm()
    }

    val onClick = { Alerts.showTimePicker(context, calendar!!, callback) }
    setOnClickListener { onClick.invoke() }
    return onClick
}

fun TextView.updateBadge(count: Int) {
    if (count > 0) {
        text = if (count > 99) {
            "99"
        } else {
            count.toString()
        }

        visible()
    } else {
        gone()
    }
}

fun TextView.underline() {
    paintFlags = paintFlags xor Paint.UNDERLINE_TEXT_FLAG
}

private fun TextView.configDatePickerListener(
    calendar: Calendar,
    callback: ((String) -> Unit)?
): DatePickerDialog.OnDateSetListener {
    val callbackBirthday = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val result = calendar.to__dd_MM_yyyy()

        text = result
        callback?.let { result }
    }
    return callbackBirthday
}

