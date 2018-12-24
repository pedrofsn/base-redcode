package br.com.redcode.base.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pedrofsn on 16/10/2017.
 */

object Constants {

    const val INVALID_VALUE = -1
    const val EMPTY_STRING = ""
    const val ONE_SECOND_IN_MILLISECONDS: Long = 1000
    const val DEFAULT_DATE_FORMAT_JSON_DB: String = "yyyy-MM-dd HH:mm:ss"

    val SDF_BRAZILIAN_DATE_AND_TIME = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
    val SDF_BRAZILIAN_DATE = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val SDF_MYSQL_DATE = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val SDF_TIME_24H = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    const val ERROR_API_KEEP_CURRENT_SCREEN = 1
    const val ERROR_API_BACK_TO_PREVIOUS_ACTIVITY = 2
    const val ERROR_API_CLEAN_AND_FORCE_LOGIN = 3
    const val ERROR_API_TOKEN_EXPIRATION = 4

}