package com.example.foodyrecipes.ui.recipes.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.foodyrecipes.data.network.dto.RecipeResult

class FoodAdapter : ListAdapter<RecipeResult, FoodViewHolder>(RecipeDiffCallBack) {

    private var onClickListener: ((RecipeResult, View) -> Unit)? = null

    fun setItemOnClickListener(onClick: (RecipeResult, View) -> Unit) {
        onClickListener = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }
    companion object RecipeDiffCallBack : DiffUtil.ItemCallback<RecipeResult>() {
        override fun areItemsTheSame(oldItem: RecipeResult, newItem: RecipeResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecipeResult, newItem: RecipeResult): Boolean {
            return oldItem == newItem
        }
    }

}
