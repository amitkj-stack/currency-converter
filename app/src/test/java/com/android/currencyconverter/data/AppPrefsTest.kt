package com.android.currencyconverter.data

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class AppPrefsTest {
    private val context : Context = ApplicationProvider.getApplicationContext()
    private val appPrefs = AppPrefs(context)

    @Before
    fun setUp() {
        clearSharedPrefs()
    }

    private fun clearSharedPrefs() = context
        .getSharedPreferences("${context.packageName}.properties", Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()

    @Test
    fun whenNoValueExistsShouldReturn0ByDefault() {
        val actual = appPrefs.timestampInSeconds
        assertThat(actual).isEqualTo(AppPrefs.NO_DATA)
    }

    @Test
    fun timestampShouldReturnCorrectValueWhenSetTo_1604779208() {
        appPrefs.timestampInSeconds = 1_604_779_208L
        val actual = appPrefs.timestampInSeconds
        val expected = 1_604_779_208L
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun timestampShouldReturnCorrectValueWhenSetTo_1748522368() {
        appPrefs.timestampInSeconds = 1_748_522_368L
        val actual = appPrefs.timestampInSeconds
        val expected = 1_748_522_368L
        assertThat(actual).isEqualTo(expected)
    }
}