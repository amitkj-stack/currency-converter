package com.android.currencyconverter.view.selector

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.currencyconverter.data.Repository
import com.android.currencyconverter.model.CurrencyModel
import com.android.currencyconverter.model.Status
import com.android.currencyconverter.utils.Utils
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.util.*

@ActivityRetainedScoped
class CurrencySelectorViewModel @ViewModelInject constructor(
    application: Application, private val repository: Repository
) : AndroidViewModel(application) {
    val status = MutableLiveData<Status>()
    val selectedCurrency = MutableLiveData<CurrencyModel>()

    val allCurrencies = repository.getAllCurrencies()


    var adapterFilteredCurrencies: ArrayList<CurrencyModel> = ArrayList()
    var adapterSelectableCurrencies: ArrayList<CurrencyModel> = ArrayList()
    private val _searchQuery = MutableLiveData<String>()

    fun handleOnClick(adapterPosition: Int) {
        selectedCurrency.postValue(adapterFilteredCurrencies[adapterPosition])
    }

    fun filter(constraint: CharSequence?): MutableList<CurrencyModel> {
        _searchQuery.postValue(constraint.toString())
        val filteredList: MutableList<CurrencyModel> = mutableListOf()
        if (constraint == null || constraint.isEmpty()) {
            filteredList.addAll(adapterSelectableCurrencies)
        } else {
            val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
            adapterSelectableCurrencies.forEach { currency ->
                val currencyCode = currency.trimmedCurrencyCode.toLowerCase(Locale.ROOT)
                val currencyName =
                    Utils.getStringResourceByName(currency.currencyCode, getApplication())
                        .toLowerCase(Locale.ROOT)
                if (currencyCode.contains(filterPattern) || currencyName.contains(filterPattern)) {
                    filteredList.add(currency)
                }
            }
        }
        return filteredList
    }
}