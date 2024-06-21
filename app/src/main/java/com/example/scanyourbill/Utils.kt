package com.example.scanyourbill

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun formatNumber(number: Int): String {
    val formatter = NumberFormat.getInstance(Locale.GERMAN)
    return formatter.format(number)
}

fun formatCurrency(prefix: String, number: Int): String {
    return if (number < 0) {
        "-$prefix${formatNumber(-number)}"
    } else {
        "$prefix${formatNumber(number)}"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun extractDateInfo(dateStr: String): Map<String, String> {
    // Parse the input date string
    val date = LocalDate.parse(dateStr)

    // Extract the required components
    val dayOfMonth = date.dayOfMonth.toString()
    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val monthYear = date.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH))

    // Create a map to hold the extracted information
    val dateInfoMap = mutableMapOf<String, String>()
    dateInfoMap["dateNumber"] = dayOfMonth
    dateInfoMap["day"] = dayOfWeek
    dateInfoMap["monthYear"] = monthYear

    return dateInfoMap
}

fun getMonthDetails(dateString: String): Map<String, String> {
    // Parse the input date string into a LocalDate object
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(dateString, formatter)

    // Format the current month and year
    val currentMonthYear = date.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH))

    // Calculate the previous month and year
    val lastMonthYear = if (date.monthValue == 1) {
        "${date.month.minus(1)} ${date.year.minus(1)}"
    } else {
        "${date.month.minus(1)} ${date.year}"
    }

    // Calculate the next month and year
    val nextMonthYear = if (date.monthValue == 12) {
        "${date.month.plus(1)} ${date.year.plus(1)}"
    } else {
        "${date.month.plus(1)} ${date.year}"
    }

    // Prepare the map with formatted month details
    val monthDetails = mapOf(
        "lastMonth" to lastMonthYear,
        "thisMonth" to currentMonthYear,
        "nextMonth" to nextMonthYear
    )

    return monthDetails
}
