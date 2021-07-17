package com.android.currencyconverter.data

import androidx.lifecycle.LiveData
import com.android.currencyconverter.model.CurrencyModel
import com.android.currencyconverter.model.Resource


interface Repository {
    var isDataEmpty: Boolean
    val lastUpdatedTimeInMilliSeconds: Long
    fun getAllCurrencies(): LiveData<MutableList<CurrencyModel>>
    fun insertCurrency(currency: CurrencyModel)
    fun insertCurrencies(currencies: List<CurrencyModel>)
    suspend fun getCurrency(currencyCode: String): CurrencyModel
    suspend fun fetchCurrencies(): Resource
}