package com.example.myapplication.util

import android.content.Context
import com.example.myapplication.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */


fun getDateInUIFormat(seconds: Long, context: Context): String {
    val formatter: DateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = seconds * 1000
    val today = Calendar.getInstance()
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DATE, 1)
    return if (calendar[Calendar.YEAR] == today[Calendar.YEAR] && calendar[Calendar.DAY_OF_YEAR] == today[Calendar.DAY_OF_YEAR]
    ) {
        context.getString(R.string.today)
    } else if (calendar[Calendar.YEAR] == tomorrow[Calendar.YEAR] && calendar[Calendar.DAY_OF_YEAR] == tomorrow[Calendar.DAY_OF_YEAR]
    ) {
        context.getString(R.string.tomorrow)
    } else {
        formatter.format(calendar.time)
    }

}

fun getTime(seconds: Long?): String? {
    return if (seconds != null) {
        val formatter: DateFormat = SimpleDateFormat("hh a", Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = seconds * 1000
        formatter.format(calendar.time)
    } else null

}
