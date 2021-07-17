package com.android.currencyconverter.view.selector

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.currencyconverter.R
import com.android.currencyconverter.adapter.SelectorAdapter
import com.android.currencyconverter.core.BaseActivity
import com.android.currencyconverter.databinding.ActivityCurrencySelectorBinding
import com.android.currencyconverter.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped


@AndroidEntryPoint
@ActivityScoped
class CurrencySelectorActivity :
    BaseActivity<ActivityCurrencySelectorBinding, CurrencySelectorViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.vm = viewModel
        initViewsAndAdapter()
        observeObservables()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        initMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initMenu(menu: Menu) {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_GO
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun initViewsAndAdapter() {
        adapter = SelectorAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun observeObservables() {
        viewModel.status.observe(this, Observer {
            showSnackMessage(it.message)
        })
        viewModel.allCurrencies.observe(this, { currencies ->
            adapter.setCurrencies(currencies)
        })
        viewModel.selectedCurrency.observe(this, Observer {
            val intent = Intent()
            intent.putExtra(Constants.EXTRA_DATA, it)
            setResult(RESULT_OK, intent)
            onBackPressed()
        })
    }

    private lateinit var adapter: SelectorAdapter
    override val viewModelClass: Class<CurrencySelectorViewModel>
        get() = CurrencySelectorViewModel::class.java
    override val layoutId: Int
        get() = R.layout.activity_currency_selector
}

