package com.android.currencyconverter.data

import android.content.Context
import com.android.currencyconverter.R
import com.android.currencyconverter.model.ApiEndPoint
import com.android.currencyconverter.model.CurrencyModel
import com.android.currencyconverter.model.ExchangeRates
import com.android.currencyconverter.model.Resource
import com.android.currencyconverter.utils.Utils.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val context: Context,
    private val exchangeRateService: ExchangeRateService,
    private val currencyDao: CurrencyDao,
    private val appPrefs: AppPrefs
) : Repository {

    override var isDataEmpty: Boolean = true
        get() = appPrefs.isDataEmpty

    override var lastUpdatedTimeInMilliSeconds: Long = 0
        get() = appPrefs.timestampInMilliSeconds


    override fun getAllCurrencies() = currencyDao.getAllCurrencies()

    override fun insertCurrency(currency: CurrencyModel) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao.insertCurrency(currency)
        }
    }

    override fun insertCurrencies(currencies: List<CurrencyModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao.insertCurrencies(currencies)
        }
    }

    override suspend fun getCurrency(currencyCode: String) = currencyDao.getCurrency(currencyCode)

    /**
     * Makes an API call and persists the response if it's successful.
     */
    override suspend fun fetchCurrencies(): Resource {
        if (context.isNetworkAvailable()) {
            val retrofitResponse: Response<ApiEndPoint>
            try {
                retrofitResponse = exchangeRateService.getExchangeRates(getApiKey())
            } catch (e: Exception) {
                return if (e is SocketTimeoutException) Resource.Error(NETWORK_TIMEOUT_ERROR_MESSAGE) else Resource.Error(
                    e.message
                )
            }
            return if (retrofitResponse.isSuccessful) {
                saveResponse(appPrefs.isDataEmpty, retrofitResponse)
                appPrefs.timestampInMilliSeconds = System.currentTimeMillis()
                Resource.Success
            } else {
                // Retrofit call executed but response wasn't in the 200s
                Resource.Error(parseError(retrofitResponse))
            }
        } else {
            return Resource.Error(NETWORK_OR_DATA_UNAVAILABLE_ERROR_MESSAGE)
        }
    }

    private fun parseError(response: Response<ApiEndPoint>): String? {
        response.errorBody()?.let { responseBody ->
            return JSONObject(responseBody.string()).optJSONObject("error")?.optString("message")
        }
        return null
    }

    private suspend fun saveResponse(isDataEmpty: Boolean, response: Response<ApiEndPoint>) {
        response.body()?.let { responseBody ->
            responseBody.exchangeRates?.let { exchangeRates ->
                persistCurrencies(isDataEmpty, exchangeRates)
            }
        }
    }

    private suspend fun persistCurrencies(isDataEmpty: Boolean, exchangeRates: ExchangeRates) {
        when {
            isDataEmpty -> currencyDao.insertCurrencies(exchangeRates.currencies)
            else -> currencyDao.updateExchangeRates(exchangeRates.currencies)
        }
    }

    private fun getApiKey(): String {
        with(context.resources) {
            return getString(R.string.openexchangerates_api_key)
        }
    }

    companion object {
        const val NETWORK_OR_DATA_UNAVAILABLE_ERROR_MESSAGE =
            "Network is unavailable and no local data found."
        const val NETWORK_TIMEOUT_ERROR_MESSAGE = "Network request timed out."
    }
}