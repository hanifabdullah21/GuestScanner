package com.hanifabdullah.data.util

import java.text.SimpleDateFormat
import java.util.*

const val LOCALE_IN = "in"
const val LOCALE_ID = "ID"

const val SDF_DEFAULT = "yyyy-MM-dd"
const val SDF_TYPE1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val SDF_TYPE2 = "dd MMMM yyyy HH:mm"
const val SDF_TYPE3 = "dd MMMM yyyy"
const val SDF_TYPE5 = "dd MMM yyyy"
const val SDF_TYPE7 = "EEEE, dd MMMM yyyy"
const val SDF_TYPE4 = "yyyy-MM-dd'T'HH:mm:ss.SSS"
const val SDF_TYPE6 = "yyyy-MM-dd HH:mm:ss"
const val SDF_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

val locale = Locale(LOCALE_IN, LOCALE_ID)

fun formatDateCustomToCustom(
    stringDate: String,
    firstFormat: String,
    formatResult: String
): String {
    if (stringDate == "") {
        return "-"
    } else {
        val sdf = SimpleDateFormat(firstFormat, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val parseDate = sdf.parse(stringDate)

        val sdf2 = SimpleDateFormat(formatResult, Locale(LOCALE_IN, LOCALE_ID))
        return sdf2.format(parseDate)
    }
}

fun formatDateCustomToCustom(stringDate: Date, formatResult: String): String {
    val sdf2 = SimpleDateFormat(formatResult, Locale(LOCALE_IN, LOCALE_ID))
    return sdf2.format(stringDate)
}

fun parseToCalendar(stringDate: String?, formatDate: String): Calendar {
    val calendar = Calendar.getInstance()

    val sdf = SimpleDateFormat(formatDate, Locale.getDefault())
    if (!stringDate.isNullOrBlank() && !stringDate.isNullOrEmpty()) {
        val date = sdf.parse(stringDate)
        calendar.time = date
    }

    return calendar
}