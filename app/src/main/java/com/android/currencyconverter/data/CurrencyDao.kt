package com.android.currencyconverter.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.currencyconverter.model.CurrencyModel

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: CurrencyModel)

    @Transaction
    suspend fun insertCurrencies(currencies: List<CurrencyModel>) {
        currencies.forEach {
            insertCurrency(it)
        }
    }

    @Transaction
    suspend fun updateExchangeRates(currencies: List<CurrencyModel>) {
        currencies.forEach { updateExchangeRate(it.currencyCode, it.exchangeRate) }
    }

    @Query("UPDATE table_currency SET column_exchangeRate = :exchangeRate WHERE column_currencyCode = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double)

    @Query("SELECT * FROM table_currency WHERE column_currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): CurrencyModel

    @Query("SELECT * FROM table_currency ORDER BY column_currencyCode ASC")
    fun getAllCurrencies(): LiveData<MutableList<CurrencyModel>>
}
