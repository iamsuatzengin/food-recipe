package com.example.foodyrecipes.ui.recipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyrecipes.databinding.FoodItemBinding
import com.example.foodyrecipes.data.network.dto.RecipeResult

class FoodAdapter : ListAdapter<RecipeResult, FoodViewHolder>(RecipeDiffCallBack()) {

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

}

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

class RecipeDiffCallBack : DiffUtil.ItemCallback<RecipeResult>() {
    override fun areItemsTheSame(oldItem: RecipeResult, newItem: RecipeResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeResult, newItem: RecipeResult): Boolean {
        return oldItem == newItem
    }
}
