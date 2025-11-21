package com.example.milsaborestest.util

import java.text.NumberFormat
import java.util.Locale

/**
 * Extensiones útiles para Kotlin
 */

/**
 * Formatea un número como moneda chilena
 */
fun Int.formatPrice(): String {
    return NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(this)
}

/**
 * Formatea un número como moneda chilena
 */
fun Long.formatPrice(): String {
    return NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(this)
}

/**
 * Formatea un Double para mostrar rating
 */
fun Double.formatRating(): String {
    return String.format(Locale.getDefault(), "%.1f", this)
}

