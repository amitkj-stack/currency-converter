package com.android.currencyconverter.model

sealed class Resource {
    object Success : Resource()
    class Error(val message: String?) : Resource()
}