package com.android.currencyconverter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.android.currencyconverter.utils.Utils
import com.android.currencyconverter.utils.Utils.EMPTY
import com.android.currencyconverter.utils.Utils.roundToTwoDecimalPlaces
import java.io.Serializable
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@Entity(tableName = "table_currency")
data class CurrencyModel(
    @PrimaryKey
    @ColumnInfo(name = "column_currencyCode")
    val currencyCode: String,
    @ColumnInfo(name = "column_exchangeRate")
    val exchangeRate: Double
) : Serializable {

    /**
     * Currency code without the "USD_" prefix.
     * Example: USD_EUR -> EUR
     */
    val trimmedCurrencyCode
        get() = if (currencyCode.length > 4) currencyCode.substring(CURRENCY_CODE_START_INDEX) else currencyCode

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CurrencyModel
        if (currencyCode != other.currencyCode) return false
        return true
    }

    override fun hashCode() = currencyCode.hashCode()

    companion object {
        const val CURRENCY_CODE_START_INDEX = 4
    }
}