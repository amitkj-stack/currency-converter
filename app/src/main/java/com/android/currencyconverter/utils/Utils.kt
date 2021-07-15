package com.android.currencyconverter.utils


/**
 * Utility and extension functions that are used across the project.
 * @JvmStatic annotations are used so Data Binding can recognize them.
 */
object Utils {
    fun Long.toMillis() = this * 1_000L
}