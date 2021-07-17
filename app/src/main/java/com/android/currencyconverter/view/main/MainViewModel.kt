package com.android.currencyconverter.view.main

import android.app.Application
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.currencyconverter.data.Repository
import com.android.currencyconverter.model.Resource
import com.android.currencyconverter.model.Status
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ActivityRetainedScoped
class MainViewModel @ViewModelInject constructor(
    application: Application, private val repository: Repository
) : AndroidViewModel(application) {
    val status = MutableLiveData<Status>()
    val isLoading = ObservableField<Boolean>()

    fun fetchCurrencies(): Unit {
        isLoading.set(true)
        if (repository.isDataEmpty) {
            viewModelScope.launch(Dispatchers.IO) {
                when (val result = repository.fetchCurrencies()) {
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
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
        } else {
            status.postValue(Status(true))
        }
    }
}