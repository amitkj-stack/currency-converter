package com.android.currencyconverter.utils

import com.android.currencyconverter.utils.Utils.roundToFourDecimalPlaces
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class UtilsTest {
    @Test
    fun testDecimalZero() {
        val value = BigDecimal("0.00000000000")
        val expected = BigDecimal("0.0000")
        val actual = value.roundToFourDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testIntegerZero() {
        val value = BigDecimal("0")
        val expected = BigDecimal("0.0000")
        val actual = value.roundToFourDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithFourDecimalPlaces() {
        val value = BigDecimal("12345.12345")
        val expected = BigDecimal("12345.1234")
        val actual = value.roundToFourDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithLessThanFourDecimalPlaces() {
        val value = BigDecimal("12345.12")
        val expected = BigDecimal("12345.1200")
        val actual = value.roundToFourDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithMoreThanFourDecimalPlacesRoundedUp() {
        val value = BigDecimal("12345.12345123")
        val expected = BigDecimal("12345.1235")
        val actual = value.roundToFourDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithMoreThanFourDecimalPlacesRoundedDown() {
        val value = BigDecimal("12345.1234123")
        val expected = BigDecimal("12345.1234")
        val actual = value.roundToFourDecimalPlaces()
        assertEquals(expected, actual);
    }

    @Test
    fun testDecimalWithMoreThanFourDecimalPlacesEndingWithFives() {
        val value = BigDecimal("12345.55555555555555")
        val expected = BigDecimal("12345.5556")
        val actual = value.roundToFourDecimalPlaces()
        assertEquals(expected, actual);
    }
}