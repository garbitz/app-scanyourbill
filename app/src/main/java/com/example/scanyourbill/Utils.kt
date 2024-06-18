package com.example.scanyourbill

import java.text.NumberFormat
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
