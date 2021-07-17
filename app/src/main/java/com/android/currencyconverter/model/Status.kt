package com.android.currencyconverter.model

data class Status(val status: Boolean, val message: String?) {
    constructor(status: Boolean) : this(status, null)
    constructor(message: String?) : this(false, message)
}