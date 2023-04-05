package com.example.foodyrecipes.ui.recipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyrecipes.data.network.dto.RecipeResult
import com.example.foodyrecipes.databinding.FoodItemBinding

class FoodViewHolder(
    private val binding: FoodItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecipeResult, onClickListener: ((RecipeResult, View) -> Unit)?) {
        binding.food = item
        binding.foodCardView.setOnClickListener {
            onClickListener?.invoke(item, binding.ivFood)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): FoodViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FoodItemBinding.inflate(layoutInflater, parent, false)
            return FoodViewHolder(binding)
        }
    }
}
