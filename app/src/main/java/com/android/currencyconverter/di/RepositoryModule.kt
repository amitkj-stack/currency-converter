package com.android.currencyconverter.di

import android.content.Context
import com.android.currencyconverter.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        exchangeRateService: ExchangeRateService,
        currencyDao: CurrencyDao,
        appPrefs: AppPrefs
    ): Repository {
        return CurrencyRepository(context, exchangeRateService, currencyDao, appPrefs)
    }
}