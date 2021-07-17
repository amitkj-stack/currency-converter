package com.android.currencyconverter.view.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import com.android.currencyconverter.R
import com.android.currencyconverter.core.BaseActivity
import com.android.currencyconverter.databinding.ActivityMainBinding
import com.android.currencyconverter.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@AndroidEntryPoint
@ActivityScoped
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.status.observe(this, Observer {
            if (it.status) {
                startNextActivity()
            } else {
                showSnackMessage(it.message)
            }
        })
        viewModel.fetchCurrencies()
    }

    private fun startNextActivity() {
        /*delay so user can see splash screen*/
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 1000)

    }

    fun updateLoadingView(isLoading: Boolean) {
        binding.retry.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java
    override val layoutId: Int
        get() = R.layout.activity_main
}