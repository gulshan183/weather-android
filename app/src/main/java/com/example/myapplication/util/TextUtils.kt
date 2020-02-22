package com.example.myapplication.util

import android.annotation.SuppressLint

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */

/**
 * Capitalize a string. For e.g.
 * ```
 * "clear sky".toTitleCase()
 * ```
 * will return "Clear Sky"
 *
 * @return Capitalized form of string
 */
@SuppressLint("DefaultLocale")
fun String.toTitleCase(): String? {
    return split(" ").joinToString(" ") { it.capitalize() }
}

/**
 * Convert a string in to degree format.
 *
 * @return
 */
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

/**
 * Add wind unit to string. Currently only supports '**m/s**' format
 *
 * @return
 */
fun String.toWindFormat(): String? {
    return "$this m/s"
}