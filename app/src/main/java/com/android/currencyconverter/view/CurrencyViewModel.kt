package com.android.currencyconverter.view

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.currencyconverter.data.Repository
import androidx.hilt.lifecycle.ViewModelInject
import com.android.currencyconverter.model.Resource
import com.android.currencyconverter.utils.Utils.toMillis
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.text.SimpleDateFormat
import java.util.*

@ActivityRetainedScoped
class CurrencyViewModel @ViewModelInject constructor(
    application: Application, private val repository: Repository) : AndroidViewModel(application) {

    @SuppressLint("SimpleDateFormat")
    fun getFormattedLastUpdate(): String {
        val timestampInMillis = repository.timestampInSeconds.toMillis()
        val date = Date(timestampInMillis)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.format(date)
    }

    fun isDataEmpty(): Boolean {
        return repository.isDataEmpty
    }

    suspend fun fetchCurrencies(): Resource {
        return repository.fetchCurrencies()
    }

}