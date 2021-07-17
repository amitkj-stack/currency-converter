package com.android.currencyconverter.data

import android.content.Context

class AppPrefs(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(
        "${context.packageName}.prop",
        Context.MODE_PRIVATE
    )
    private val editor = sharedPrefs.edit()

    var timestampInMilliSeconds: Long
        get() = (sharedPrefs.getLong(TIMESTAMP, NO_DATA))
        set(value) = editor.putLong(TIMESTAMP, value).apply()

    val isDataEmpty
        get() = timestampInMilliSeconds == NO_DATA


    companion object {
        const val TIMESTAMP = "timestamp"
        const val NO_DATA = 0L
    }
}