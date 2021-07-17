package com.android.currencyconverter.di

import com.android.currencyconverter.BuildConfig
import com.android.currencyconverter.data.ExchangeRateService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideExchangeRateService(retrofit: Retrofit): ExchangeRateService {
        return retrofit.create(ExchangeRateService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        @Named("okHttpClient") okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return ExchangeRateService.BASE_URL
    }

    @Singleton
    @Provides
    @Named("bodyHttpLoggingInterceptor")
    fun bodyHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    @Named("okHttpClient")
    fun provideOkHttpClient(
        @Named("bodyHttpLoggingInterceptor") bodyHttpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val TIMEOUT_IN_SEC = 30L
        val okHttpClientBuilder = OkHttpClient().newBuilder()
            .connectTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
        //                .addInterceptor(apiRetrialInterceptor)
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(bodyHttpLoggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }
}