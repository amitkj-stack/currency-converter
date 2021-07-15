package com.android.currencyconverter

import android.os.Bundle
import android.view.View
import com.android.currencyconverter.core.BaseActivity
import com.android.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@AndroidEntryPoint
@ActivityScoped
class MainActivity : BaseActivity<ActivityMainBinding, CurrencyViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val viewModelClass: Class<CurrencyViewModel>
        get() = CurrencyViewModel::class.java
    override val layoutId: Int
        get() = R.layout.activity_main
}