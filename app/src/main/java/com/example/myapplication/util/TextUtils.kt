package com.example.myapplication.util

import android.annotation.SuppressLint

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */


@SuppressLint("DefaultLocale")
fun String.toTitleCase(): String? {
    return split(" ").joinToString(" ") { it.capitalize() }
}

fun String.toDegreeFormat(): String? {
    return this + "\u00B0"
}

fun getMinMaxDegreeFormat(maxTemp: String?, minTemp: String?): String? {
    return if (maxTemp == null || minTemp == null) {
        null
    } else {
        "${maxTemp.toDegreeFormat()} / ${minTemp.toDegreeFormat()}"
    }

}

fun String.toWindFormat(): String? {
    return "$this m/s"
}