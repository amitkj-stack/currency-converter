package com.android.currencyconverter.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("image_src")
        fun setImageSrc(imageView: ImageView, resource: Int) {
            if (resource == 0 || resource == -1) return
            imageView.setImageResource(resource)
        }
    }

}