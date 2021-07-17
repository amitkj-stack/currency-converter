package com.android.currencyconverter.view.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.android.currencyconverter.R
import com.android.currencyconverter.core.BaseActivity
import com.android.currencyconverter.databinding.ActivityHomeBinding
import com.android.currencyconverter.model.CurrencyModel
import com.android.currencyconverter.model.Status
import com.android.currencyconverter.utils.Constants
import com.android.currencyconverter.view.selector.CurrencySelectorActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@AndroidEntryPoint
@ActivityScoped
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.etField.requestFocus()
        binding.info.text = viewModel.runConversions()
    }

    @Suppress("DEPRECATION")
    private fun initListener() {
        binding.btnFromCurrency.setOnClickListener {
            binding.etField.setText("")
            val intent = Intent(this, CurrencySelectorActivity::class.java)
            startActivityForResult(intent, REQ_CODE_GET_FROM_CURRENCY)
        }
        binding.btnToCurrency.setOnClickListener {
            binding.etField.setText("")
            val intent = Intent(this, CurrencySelectorActivity::class.java)
            startActivityForResult(intent, REQ_CODE_GET_TO_CURRENCY)
        }
        binding.etField.addTextChangedListener(textWatcherListener)
    }

    private fun removeListener() {
        binding.btnFromCurrency.setOnClickListener(null)
        binding.btnToCurrency.setOnClickListener(null)
        binding.etField.removeTextChangedListener(textWatcherListener)
    }

    private fun observeObservables() {
        viewModel.status.observe(this, statusObserver)
        viewModel.isSwapped.observe(this, isSwappedObserver)
    }

    private fun removeObservables() {
        viewModel.status.removeObserver(statusObserver)
        viewModel.isSwapped.removeObserver(isSwappedObserver)
    }

    private var isSwappedObserver: Observer<Boolean> = Observer {
        binding.info.text = viewModel.runConversions()
        viewModel.runConversions(
            try {
                binding.etField.text.toString().toDouble()
            } catch (ex: NumberFormatException) {
                0.0
            }
        )
    }

    private var statusObserver: Observer<Status> = Observer {
        showSnackMessage(binding.inputLayout, it.message)
    }

    override fun onResume() {
        super.onResume()
        observeObservables()
        initListener()
    }

    override fun onPause() {
        super.onPause()
        removeObservables()
        removeListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK)
            return
        when (requestCode) {
            REQ_CODE_GET_FROM_CURRENCY -> {
                viewModel.fromCurrency.set(data?.getSerializableExtra(Constants.EXTRA_DATA) as CurrencyModel?)
            }
            REQ_CODE_GET_TO_CURRENCY -> {
                viewModel.toCurrency.set(data?.getSerializableExtra(Constants.EXTRA_DATA) as CurrencyModel?)
            }
        }
        binding.info.text = viewModel.runConversions()
    }

    val textWatcherListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.runConversions(
                try {
                    s.toString().toDouble()
                } catch (ex: NumberFormatException) {
                    0.0
                }
            )
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java
    override val layoutId: Int
        get() = R.layout.activity_home

    companion object {
        const val REQ_CODE_GET_FROM_CURRENCY = 101
        const val REQ_CODE_GET_TO_CURRENCY = 102
    }
}