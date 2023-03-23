package com.example.foodyrecipes.ui.recipes.component

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.foodyrecipes.R
import com.example.foodyrecipes.databinding.NoInternetConnectionBinding

class NoInternetConnection @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attributeSet, defStyleAttr) {

    private val binding = NoInternetConnectionBinding.inflate(
        LayoutInflater.from(context), this, false
    )
    private lateinit var wifiAnimation: AnimationDrawable

    init {
        addView(binding.root)
    }

    fun setAnimation(): NoInternetConnection{
        binding.imageView.apply {
            setBackgroundResource(R.drawable.wifi_animation_list)
            wifiAnimation = background as AnimationDrawable
        }
        return this
    }

    fun startAnimation(){
        wifiAnimation.start()
    }

    fun stopAnimation(){
        wifiAnimation.stop()
    }
}