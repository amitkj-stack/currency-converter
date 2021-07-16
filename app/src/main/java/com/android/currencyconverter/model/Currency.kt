
package com.android.currencyconverter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.android.currencyconverter.utils.Utils.EMPTY
import com.android.currencyconverter.utils.Utils.roundToFourDecimalPlaces
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@Entity(tableName = "table_currency")
data class Currency(@PrimaryKey
                    @ColumnInfo(name = "column_currencyCode")
                    val currencyCode: String,
                    @ColumnInfo(name = "column_exchangeRate")
                    val exchangeRate: Double) {

    @Ignore
    var isFocused = false

    @Ignore
    var conversion = Conversion(BigDecimal.ZERO)

    @Ignore
    private var decimalFormatter: DecimalFormat

    @Ignore
    private var decimalSeparator: String

    init {
        val numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault())
        val conversionPattern = "#,##0.####"
        decimalFormatter = numberFormatter as DecimalFormat
        decimalFormatter.applyPattern(conversionPattern)
        decimalSeparator = decimalFormatter.decimalFormatSymbols.decimalSeparator.toString()
    }


    /**
     * Currency code without the "USD_" prefix.
     * Example: USD_EUR -> EUR
     */
    val trimmedCurrencyCode
        get() = currencyCode.substring(CURRENCY_CODE_START_INDEX)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Currency
        if (currencyCode != other.currencyCode) return false
        return true
    }

    override fun hashCode() = currencyCode.hashCode()

    /**
     * Since the toString() method is really only useful for debugging I've structured it in a way
     * which concisely displays the object's state.
     *
     * Example: 4 S* F* EUR
     *          | |  |   |
     *      Order |  |   |
     *     Selected? |   |
     *         Focused?  |
     *            Currency code
     *
     *    *blank if not selected/focused
     */
    inner class Conversion(conversionValue: BigDecimal) {
        /**
         * The underlying numeric conversion result.
         * Example: 1234.5678
         */
        var conversionValue: BigDecimal = conversionValue
            set(value) {
                field = value.roundToFourDecimalPlaces()
                conversionString = field.toString()
            }

        /**
         * The [conversionValue] as a String.
         * Example: "1234.5678"
         */
        var conversionString = String.EMPTY

        /**
         * The [conversionString] formatted according to locale.
         * Example:    USA: 1,234.5678
         *          France: 1.234,5678
         */
        val conversionText: String
            get() {
                return if (conversionString.isNotBlank()) {
                    decimalFormatter.format(conversionString)
                } else {
                    String.EMPTY
                }
            }
    }

    companion object {
        const val CURRENCY_CODE_START_INDEX = 4
    }
}