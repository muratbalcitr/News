package com.m2lifeapps.challenge.core.common

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun formatDate(date: String): String {
        val birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
        val calendar = Calendar.getInstance().apply { time = birthDate!! }
        val year = calendar.get(Calendar.YEAR)
        val month = if (calendar.get(Calendar.MONTH) + 1 < 10)
            "0" + (calendar.get(Calendar.MONTH) + 1)
        else
            calendar.get(Calendar.MONTH) + 1
        val day = if (calendar.get(Calendar.DAY_OF_MONTH) + 1 < 10)
            "0" + (calendar.get(Calendar.DAY_OF_MONTH) + 1)
        else
            calendar.get(Calendar.DAY_OF_MONTH) + 1
        return "$day.$month.$year"
    }
    fun formatDateTime(date: String): String {
         val birthDate = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault()).parse(date)
        val calendar = Calendar.getInstance().apply { time = birthDate!! }
        val year = calendar.get(Calendar.YEAR)
        val month = if (calendar.get(Calendar.MONTH) + 1 < 10)
            "0" + (calendar.get(Calendar.MONTH) + 1)
        else
            calendar.get(Calendar.MONTH) + 1
        val day = if (calendar.get(Calendar.DAY_OF_MONTH) + 1 < 10)
            "0" + (calendar.get(Calendar.DAY_OF_MONTH) + 1)
        else
            calendar.get(Calendar.DAY_OF_MONTH) + 1
        val hour =calendar.get(Calendar.HOUR)
        val minute =calendar.get(Calendar.MINUTE)
        return "$day.$month.$year $hour:$minute"
    }
}