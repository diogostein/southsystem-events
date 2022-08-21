package com.codelabs.southsystem.eventos.core.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "dd/MM/yyyy"): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun BigDecimal.toBrazilianCurrency(): String {
    return NumberFormat
        .getCurrencyInstance(Locale("pt", "BR"))
        .format(this)
}

fun BigDecimal.toDecimal(): String {
    return NumberFormat
        .getNumberInstance(Locale("pt", "BR"))
        .format(this)
}