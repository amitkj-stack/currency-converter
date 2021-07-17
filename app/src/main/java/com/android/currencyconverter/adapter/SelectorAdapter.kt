package com.android.currencyconverter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.currencyconverter.databinding.RowSelectorBinding
import com.android.currencyconverter.model.CurrencyModel
import com.android.currencyconverter.utils.CurrencyDiffUtilCallback
import com.android.currencyconverter.view.selector.CurrencySelectorViewModel

class SelectorAdapter(private val viewModel: CurrencySelectorViewModel) :
    ListAdapter<CurrencyModel, SelectorAdapter.ViewHolder>(CurrencyDiffUtilCallback()),
    Filterable {

    inner class ViewHolder(private val binding: RowSelectorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                viewModel.handleOnClick(adapterPosition)
            }
        }

        fun bind(currency: CurrencyModel) {
            binding.currency = currency
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel.adapterFilteredCurrencies[position])
    }

    fun setCurrencies(currencies: List<CurrencyModel>) {
        viewModel.adapterFilteredCurrencies = ArrayList(currencies)
        viewModel.adapterSelectableCurrencies = ArrayList(currencies)
        submitList(viewModel.adapterFilteredCurrencies)
    }

    override fun getFilter() = currenciesFilter

    private val currenciesFilter: Filter = object : Filter() {
        @SuppressLint("DefaultLocale")
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            filterResults.values = viewModel.filter(constraint)
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            val filteredCurrencies = results.values as List<CurrencyModel>
            viewModel.adapterFilteredCurrencies.run {
                clear()
                addAll(filteredCurrencies)
            }
            submitList(filteredCurrencies)
        }
    }
}