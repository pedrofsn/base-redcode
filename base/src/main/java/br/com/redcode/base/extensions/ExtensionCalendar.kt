package br.com.redcode.base.extensions

import br.com.redcode.base.utils.Constants.DEFAULT_DATE_FORMAT_JSON_DB
import br.com.redcode.base.utils.Constants.SDF_BRAZILIAN_DATE
import br.com.redcode.base.utils.Constants.SDF_BRAZILIAN_DATE_AND_TIME
import br.com.redcode.base.utils.Constants.SDF_MYSQL_DATE
import br.com.redcode.base.utils.Constants.SDF_TIME_24H
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pedrofsn on 01/12/17.
 */

fun Calendar.zeroTime(): Calendar {
    set(Calendar.HOUR, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return this
}

fun Calendar.to__dd_MM_yyyy__HH_mm(): String? {
    return SDF_BRAZILIAN_DATE_AND_TIME.format(this.time)
}

fun Calendar.to__yyyy_MM_dd__HH_mm_ss(): String? {
    return SimpleDateFormat(DEFAULT_DATE_FORMAT_JSON_DB, Locale.ENGLISH).format(this.time)
}

fun Calendar.to__dd_MM_yyyy() = SDF_BRAZILIAN_DATE.format(this.time)
fun Calendar.to__yyyy_MM_dd() = SDF_MYSQL_DATE.format(this.time)
fun Calendar.to__HH_mm() = SDF_TIME_24H.format(this.time)
fun Calendar.getShortMonth() = DateFormatSymbols().shortMonths[get(Calendar.MONTH)]

fun Calendar.getFullWeekday(): String? {
    return getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
}

fun Calendar.minimalAge(minimalAge: Int = 18): Calendar {
    set(Calendar.YEAR, get(Calendar.YEAR) - minimalAge)
    zeroTime()
    return this
}

infix fun Calendar.isSameDay(second: Calendar): Boolean {
    val isSameYear = this[Calendar.YEAR] == second[Calendar.YEAR]
    val isSameDayOfYear = this[Calendar.DAY_OF_YEAR] == second[Calendar.DAY_OF_YEAR]
    return isSameYear && isSameDayOfYear
}