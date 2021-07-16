package com.android.currencyconverter.utils

import android.content.Context
import android.net.ConnectivityManager
import java.math.BigDecimal
import java.math.RoundingMode


/**
 * Utility and extension functions that are used across the project.
 * @JvmStatic annotations are used so Data Binding can recognize them.
 */
object Utils {
    fun Long.toMillis() = this * 1_000L

    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

    fun BigDecimal.roundToFourDecimalPlaces(): BigDecimal = setScale(4, RoundingMode.HALF_DOWN)

    val String.Companion.EMPTY get() = ""
}