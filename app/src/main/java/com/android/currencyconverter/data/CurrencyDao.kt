package com.android.currencyconverter.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.currencyconverter.model.Currency

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: Currency)

    @Transaction
    suspend fun insertCurrencies(currencies: List<Currency>) {
        currencies.forEach { insertCurrency(it) }
    }

    @Transaction
    suspend fun updateExchangeRates(currencies: List<Currency>) {
        currencies.forEach { updateExchangeRate(it.currencyCode, it.exchangeRate) }
    }

    @Query("UPDATE table_currency SET column_exchangeRate = :exchangeRate WHERE column_currencyCode = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double)

    @Query("SELECT * FROM table_currency WHERE column_currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): Currency

    @Query("SELECT * FROM table_currency ORDER BY column_currencyCode ASC")
    fun getAllCurrencies(): LiveData<MutableList<Currency>>
}
