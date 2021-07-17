package com.android.currencyconverter.core

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity<B : ViewDataBinding, Vm : AndroidViewModel> : AppCompatActivity() {
    abstract val viewModelClass: Class<Vm>
    abstract val layoutId: Int
    lateinit var viewModel: Vm
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<B>(this, layoutId)
        viewModel = ViewModelProvider(this).get(viewModelClass)
    }


    protected fun showSnackMessage(message: String?) {
        message?.let {
            hideKeyboard()
            Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    protected fun showSnackMessage(anchorView: View, message: String?) {
        message?.let {
            hideKeyboard()
            Snackbar.make(anchorView, it, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}