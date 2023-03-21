package com.example.foodyrecipes.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

object BindingAdapters {

    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, url: String){
        imageView.load(url){
            crossfade(600)
        }
    }
}