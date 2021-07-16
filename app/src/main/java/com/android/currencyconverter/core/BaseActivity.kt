package com.android.currencyconverter.core

import android.os.Bundle
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.android.currencyconverter.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

abstract class BaseActivity<B: ViewDataBinding, Vm : AndroidViewModel> : AppCompatActivity() {
    abstract val viewModelClass: Class<Vm>
    abstract val layoutId: Int
    lateinit var viewModel: Vm
    lateinit var binding : B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<B>(this, layoutId)
        viewModel = ViewModelProvider(this).get(viewModelClass)
    }


    protected fun showSnackMessage(message : String?) {
        message?.let {
            Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}