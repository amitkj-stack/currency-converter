package com.android.currencyconverter.utils

import androidx.recyclerview.widget.DiffUtil
import com.android.currencyconverter.model.CurrencyModel

class CurrencyDiffUtilCallback : DiffUtil.ItemCallback<CurrencyModel>() {
    override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem.currencyCode == newItem.currencyCode
    }

    override fun areContentsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem.currencyCode == newItem.currencyCode &&
                oldItem.exchangeRate == newItem.exchangeRate
    }
}