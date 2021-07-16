package com.android.currencyconverter.data

import android.content.Context
import com.android.currencyconverter.utils.Utils.toMillis

class AppPrefs(context: Context) {

    private val sharedPrefs = context.getSharedPreferences("${context.packageName}.properties",
            Context.MODE_PRIVATE)
    private val editor = sharedPrefs.edit()

    var timestampInSeconds: Long
        get() = (sharedPrefs.getLong(TIMESTAMP, NO_DATA))
        set(value) = editor.putLong(TIMESTAMP, value).apply()

    val isDataEmpty
        get() = timeSinceLastUpdateInMillis == NO_DATA

    private val timeSinceLastUpdateInMillis: Long
        get() {
            return if (timestampInSeconds != NO_DATA) {
                System.currentTimeMillis() - timestampInSeconds.toMillis()
            } else {
                NO_DATA
            }
        }

    companion object {
        const val TIMESTAMP = "timestamp"
        const val NO_DATA = 0L
    }
}