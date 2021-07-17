package com.android.currencyconverter.view.home

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Ignore
import com.android.currencyconverter.R
import com.android.currencyconverter.app.MyApplication
import com.android.currencyconverter.data.Repository
import com.android.currencyconverter.model.CurrencyModel
import com.android.currencyconverter.model.Resource
import com.android.currencyconverter.model.Status
import com.android.currencyconverter.utils.CurrencyConversion
import com.android.currencyconverter.utils.Utils
import com.android.currencyconverter.utils.Utils.roundToTwoDecimalPlaces
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@ActivityRetainedScoped
class HomeViewModel @ViewModelInject constructor(
    application: Application, private val repository: Repository
) : AndroidViewModel(application) {

    private var decimalFormatter: DecimalFormat
    private var decimalSeparator: String

    val status = MutableLiveData<Status>()
    val isSwapped = MutableLiveData<Boolean>()
    val isLoading = ObservableField<Boolean>()
    val lastUpdated = ObservableField<String>()
    val fromCurrency = ObservableField<CurrencyModel>()
    val toCurrency = ObservableField<CurrencyModel>()
    val conversionString = ObservableField<String>()


    init {
        lastUpdated.set(getFormattedLastUpdate())
        val numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault())
        val conversionPattern = "#,##0.####"
        decimalFormatter = numberFormatter as DecimalFormat
        decimalFormatter.applyPattern(conversionPattern)
        decimalSeparator = decimalFormatter.decimalFormatSymbols.decimalSeparator.toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedLastUpdate(): String {
        val timestampInMillis = repository.lastUpdatedTimeInMilliSeconds
        val date = Date(timestampInMillis)
        val simpleDateFormat = SimpleDateFormat("dd MMM, HH:mm.ss a")
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.format(date)
    }

    fun fetchALLCurrencies(): Unit {
        isLoading.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.fetchCurrencies()) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        isLoading.set(false)
                        lastUpdated.set(getFormattedLastUpdate())
                        status.postValue(Status(true))
                    }
                }
                is Resource.Error -> {
                    withContext(Dispatchers.Main) {
                        isLoading.set(false)
                        status.postValue(Status(false, result.message))
                    }
                }
            }
        }
    }

    /**
     * Runs the conversion of all currencies against the currency's input value.
     */
    fun runConversions(value: Double) {
        if (value == 0.0) {
            conversionString.set(
                getApplication<MyApplication>().resources.getString(
                    R.string.conversion_info,
                    "...",
                    "..."
                )
            )
            return
        }
        if (fromCurrency.get() == null || toCurrency.get() == null) {
            conversionString.set(
                getApplication<MyApplication>().resources.getString(
                    R.string.conversion_info,
                    "...",
                    "..."
                )
            )
            status.postValue(Status("Please select currencies"))
            return
        }
        val conversionValue = formatConversion(CurrencyConversion.convertCurrency(
            BigDecimal(value), fromCurrency.get()!!.exchangeRate, toCurrency.get()!!.exchangeRate
        ).roundToTwoDecimalPlaces().toString())

        val roundedValue = formatConversion(BigDecimal(value).roundToTwoDecimalPlaces().toString())

        conversionString.set(
            getApplication<MyApplication>().resources.getString(
                R.string.conversion_info,
                "$roundedValue ${fromCurrency.get()!!.trimmedCurrencyCode}",
                "$conversionValue ${toCurrency.get()!!.trimmedCurrencyCode}"
            )
        )
    }

    fun runConversions(): String {
        if (fromCurrency.get() == null || toCurrency.get() == null) {
            return ""
        }
        val roundedValue = BigDecimal(1)
        val conversionValue = CurrencyConversion.convertCurrency(
            roundedValue, fromCurrency.get()!!.exchangeRate, toCurrency.get()!!.exchangeRate
        ).roundToTwoDecimalPlaces()
        return getApplication<MyApplication>().resources.getString(
            R.string.conversion_info_at_the_rate,
            "$roundedValue ${fromCurrency.get()!!.trimmedCurrencyCode}",
            "$conversionValue ${toCurrency.get()!!.trimmedCurrencyCode}"
        )
    }

    /** swap
     */
    fun swapCurrencies() {
        val currency = fromCurrency.get()
        fromCurrency.set(toCurrency.get())
        toCurrency.set(currency)
        isSwapped.postValue(true)
    }

    /**
     * Formats a numeric String with grouping separators while retaining trailing zeros.
     */
    private fun formatConversion(conversion: String): String {
        return when {
            conversion.contains(".") -> {
                val splitConversion = conversion.split(".")
                val wholePart = splitConversion[Utils.Order.FIRST.position]
                val decimalPart = splitConversion[Utils.Order.SECOND.position]
                decimalFormatter.format(BigDecimal(wholePart)) + decimalSeparator + decimalPart
            }
            else -> {
                decimalFormatter.format(BigDecimal(conversion))
            }
        }
    }
}