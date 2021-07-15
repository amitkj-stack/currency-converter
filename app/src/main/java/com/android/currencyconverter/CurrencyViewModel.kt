package com.android.currencyconverter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.currencyconverter.data.Repository
import androidx.hilt.lifecycle.ViewModelInject
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
class CurrencyViewModel @ViewModelInject constructor(application: Application) : AndroidViewModel(application) {

}