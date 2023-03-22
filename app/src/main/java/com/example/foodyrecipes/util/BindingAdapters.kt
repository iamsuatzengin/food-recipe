package com.example.foodyrecipes.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foodyrecipes.R

object BindingAdapters {

    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, url: String){
        imageView.load(url){
            crossfade(600)
        }
    }


    @BindingAdapter("setNumberOfLikes")
    @JvmStatic
    fun setNumberOfLikes(textView: TextView, likes: Int){
        textView.text = likes.toString()
    }

    @BindingAdapter("setNumberOfMinutes")
    @JvmStatic
    fun setNumberOfMinutes(textView: TextView, min: Int){
        textView.text = min.toString()
    }

    @BindingAdapter("veganColor")
    @JvmStatic
    fun veganColor(view: View, isVegan: Boolean){
        if(isVegan){
            when(view){
                is TextView -> ContextCompat.getColor(view.context, R.color.green)

                is ImageView -> ContextCompat.getColor(view.context, R.color.green)
            }
        }
    }
}