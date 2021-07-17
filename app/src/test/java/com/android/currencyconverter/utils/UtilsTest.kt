package com.android.currencyconverter.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.android.currencyconverter.R
import com.android.currencyconverter.model.CurrencyModel
import com.android.currencyconverter.utils.Utils.roundToTwoDecimalPlaces
import org.junit.Assert.assertEquals
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class UtilsTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun testDecimalZero() {
        val value = BigDecimal("0.00000000000")
        val expected = BigDecimal("0.00")
        val actual = value.roundToTwoDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testIntegerZero() {
        val value = BigDecimal("0")
        val expected = BigDecimal("0.00")
        val actual = value.roundToTwoDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithTwoDecimalPlaces() {
        val value = BigDecimal("12345.12")
        val expected = BigDecimal("12345.12")
        val actual = value.roundToTwoDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithLessThanTwoDecimalPlaces() {
        val value = BigDecimal("12345.1")
        val expected = BigDecimal("12345.10")
        val actual = value.roundToTwoDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithMoreThanTwoDecimalPlacesRoundedUp() {
        val value = BigDecimal("12345.125123")
        val expected = BigDecimal("12345.13")
        val actual = value.roundToTwoDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithMoreThanTwoDecimalPlacesRoundedDown() {
        val value = BigDecimal("12345.1234123")
        val expected = BigDecimal("12345.12")
        val actual = value.roundToTwoDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithMoreThanTwoDecimalPlacesEndingWithFives() {
        val value = BigDecimal("12345.55555555555555")
        val expected = BigDecimal("12345.56")
        val actual = value.roundToTwoDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun unitedStatesDollarReturnsCorrectStringInEnglishLocale() {
        setLocale("en", "US")
        val expected = "United States Dollar"
        val actual = Utils.getStringResourceByName("USD_USD", context)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun britishPoundReturnsCorrectStringInEnglishLocale() {
        setLocale("en", "US")
        val expected = "British Pound"
        val actual = Utils.getStringResourceByName("USD_GBP", context)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun unitedStatesDollarReturnsCorrectStringInOtherLocales() {
        setLocale("fr", "FR")
        val actual = Utils.getStringResourceByName("USD_USD", context)
        val expected = "United States Dollar"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun britishPoundReturnsCorrectStringInOtherLocales() {
        setLocale("fr", "FR")
        val expected = "British Pound"
        val actual = Utils.getStringResourceByName("USD_GBP", context)
        assertThat(actual).isEqualTo(expected)
    }

    /**
     * Credit: https://stackoverflow.com/a/21810126/5906793
     */
    private fun setLocale(language: String, country: String) {
        val locale = Locale(language, country)
        Locale.setDefault(locale)
        val res: Resources = context.resources
        val config: Configuration = res.configuration
        config.locale = locale
        res.updateConfiguration(config, res.displayMetrics)
    }

    @Test
    fun unitedStatesDollarReturnsCorrectDrawable() {
        val expected = R.drawable.usd_usd
        val actual = Utils.getDrawableResourceByName("usd_usd", context)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun britishPoundReturnsCorrectDrawable() {
        val expected = R.drawable.usd_gbp
        val actual = Utils.getDrawableResourceByName("usd_gbp", context)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun euroReturnsCorrectDrawable() {
        val expected = R.drawable.usd_eur
        val actual = Utils.getDrawableResourceByName("usd_eur", context)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun bitcoinReturnsCorrectDrawable() {
        val expected = R.drawable.usd_btc
        val actual = Utils.getDrawableResourceByName("usd_btc", context)
        assertThat(actual).isEqualTo(expected)
    }

//    @Test
//    fun conversionOnLocalesUsingCommaAsGroupingSeparatorShouldDisplayCorrectly() {
//        Locale.setDefault(Locale("en", "US"))
//        val currency = CurrencyModel("USD_EUR", 0.842993).apply {
//            conversion.conversionValue = BigDecimal("123456789.12")
//        }
//        val expected = "123,456,789.12"
//        val actual = currency.conversion.conversionText
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun conversionOnLocalesUsingPeriodAsGroupingSeparatorShouldDisplayCorrectly() {
//        Locale.setDefault(Locale("es", "ES"))
//        val currency = CurrencyModel("USD_EUR", 0.842993).apply {
//            conversion.conversionValue = BigDecimal("123456789.12")
//        }
//        val expected = "123.456.789,12"
//        val actual = currency.conversion.conversionText
//        assertThat(actual).isEqualTo(expected)
//    }
}