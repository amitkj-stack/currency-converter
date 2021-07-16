package com.android.currencyconverter.data

import android.content.Context
import com.android.currencyconverter.model.ApiEndPoint
import com.android.currencyconverter.model.Resource
import com.android.currencyconverter.utils.Utils.isNetworkAvailable
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response
import java.net.SocketTimeoutException
import com.google.common.truth.Truth.assertThat

class CurrencyRepositoryTest {

    private val context = mockk<Context>(relaxed = true)
    private val exchangeRateService = mockk<ExchangeRateService>(relaxed = true)
    private val currencyDao = mockk<CurrencyDao>(relaxed = true)
    private val appPrefs = mockk<AppPrefs>(relaxed = true)
    private val repository: Repository = CurrencyRepository(
        context,
        exchangeRateService,
        currencyDao,
        appPrefs
    )

    @Test
    fun whenNetworkIsAvailableShouldReturnSuccess() = runBlocking {
        val spy = spyk(repository)
        every { context.isNetworkAvailable() } returns true
        coEvery { exchangeRateService.getExchangeRates(any()) } returns Response.success(null)
        val expected = Resource.Success
        val actual = spy.fetchCurrencies()
        verify { spy["persistResponse"](any<Response<ApiEndPoint>>()) }
        assertThat(actual).isInstanceOf(expected.javaClass)
    }



    @Test
    fun whenNetworkIsUnavailableShouldReturnError() = runBlocking {
        every { context.isNetworkAvailable() } returns false
        val expected = Resource.Error(CurrencyRepository.NETWORK_OR_DATA_UNAVAILABLE_ERROR_MESSAGE)
        val actual = repository.fetchCurrencies()
        assertThat(actual).isInstanceOf(expected.javaClass)
        assertThat((actual as Resource.Error).message).isEqualTo(expected.message)
    }

    @Test
    fun whenNetworkIsAvailableButRetrofitCallTimesOutShouldReturnError() = runBlocking {
        every { context.isNetworkAvailable() } returns true
        coEvery { exchangeRateService.getExchangeRates(any()) } throws SocketTimeoutException()
        val expected = Resource.Error(CurrencyRepository.NETWORK_TIMEOUT_ERROR_MESSAGE)
        val actual = repository.fetchCurrencies()
        assertThat(actual).isInstanceOf(expected.javaClass)
        assertThat((actual as Resource.Error).message).isEqualTo(expected.message)
    }
}