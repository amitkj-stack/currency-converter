package com.android.currencyconverter.data

import androidx.lifecycle.LiveData
import com.android.currencyconverter.model.Currency
import com.android.currencyconverter.model.Resource


interface Repository {
    var isDataEmpty: Boolean
    val timestampInSeconds: Long
    fun getAllCurrencies(): LiveData<MutableList<Currency>>
    fun insertCurrency(currency: Currency)
    fun insertCurrencies(currencies: List<Currency>)
    suspend fun getCurrency(currencyCode: String): Currency
    suspend fun fetchCurrencies(): Resource
}