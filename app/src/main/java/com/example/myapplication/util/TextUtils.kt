package com.example.myapplication.util

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */


fun String.toTitleCase(): String? {
    return split(" ").map { it.capitalize() }.joinToString(" ")
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