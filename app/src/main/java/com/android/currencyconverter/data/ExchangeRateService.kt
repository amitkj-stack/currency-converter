package com.android.currencyconverter.data

import com.android.currencyconverter.model.ApiEndPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API endpoint: https://api.exchangeratesapi.io/v1/latest
? access_key = API_KEY
 */
interface ExchangeRateService {

    @GET("v1/latest")
    suspend fun getExchangeRates(@Query("access_key") app_id: String): Response<ApiEndPoint>

    companion object {
        const val BASE_URL = "http://api.exchangeratesapi.io/"
    }
}