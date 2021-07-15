package com.android.currencyconverter.core

import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

abstract class BaseActivity<B: ViewDataBinding, Vm : AndroidViewModel> : AppCompatActivity() {
    abstract val viewModelClass: Class<Vm>
    abstract val layoutId: Int
    protected lateinit var viewModel: Vm
    protected lateinit var binding : B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<B>(this, layoutId)
        viewModel = ViewModelProvider(this).get(viewModelClass)
    }
}