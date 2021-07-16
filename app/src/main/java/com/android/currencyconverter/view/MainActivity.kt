package com.android.currencyconverter.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.android.currencyconverter.R
import com.android.currencyconverter.core.BaseActivity
import com.android.currencyconverter.databinding.ActivityMainBinding
import com.android.currencyconverter.model.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
@ActivityScoped
class MainActivity : BaseActivity<ActivityMainBinding, CurrencyViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null) {
            if(viewModel.isDataEmpty()) {
                fetchCurrencies()
            } else{
//            startActivity()
            }
        }
    }

    private fun fetchCurrencies() {
        updateLoadingView(true)
        lifecycleScope.launch(Dispatchers.IO) {
            /**
             * Small delay so the user can actually see the splash screen
             * for a moment as feedback of an attempt to retrieve data.
             */
            delay(250)
            when (val result = viewModel.fetchCurrencies()) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        Log.e(this.javaClass.simpleName, "Success")
                    }
                }
                is Resource.Error -> {
                    withContext(Dispatchers.Main) {
                        showSnackMessage(result.message)
                        updateLoadingView(false)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.retry.setOnClickListener {
            fetchCurrencies()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.retry.setOnClickListener(null)
    }

    fun updateLoadingView(isLoading: Boolean) {
        binding.retry.visibility = if(isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override val viewModelClass: Class<CurrencyViewModel>
        get() = CurrencyViewModel::class.java
    override val layoutId: Int
        get() = R.layout.activity_main
}