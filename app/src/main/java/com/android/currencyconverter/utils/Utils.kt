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
//    fun Long.toMillis() = this * 1_000L

    enum class Order(val position: Int) {
        INVALID(-1),
        FIRST(0),
        SECOND(1),
    }

    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

    fun BigDecimal.roundToTwoDecimalPlaces(): BigDecimal = setScale(2, RoundingMode.HALF_DOWN)

    val String.Companion.EMPTY get() = ""

    /**
     * Retrieves drawable resources u`sing a String instead of an int.
     * Credit: https://stackoverflow.com/a/11595723/5906793
     */
    @JvmStatic
    fun getDrawableResourceByName(name: String?, context: Context): Int {
        return context.resources.getIdentifier(name ?: "", "drawable", context.packageName)
    }

    /**
     * Retrieves string resources using a String instead of an int.
     * Credit: https://stackoverflow.com/a/11595723/5906793
     */
    @JvmStatic
    fun getStringResourceByName(name: String?, context: Context): String {
        val resId = context.resources.getIdentifier(name ?: "", "string", context.packageName)
        return context.getString(resId)
    }
}