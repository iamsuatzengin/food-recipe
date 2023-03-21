package com.example.foodyrecipes.ui.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyrecipes.databinding.FoodItemBinding
import com.example.foodyrecipes.data.network.dto.RecipeResult

class FoodAdapter: ListAdapter<RecipeResult, FoodViewHolder>(DiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<RecipeResult>(){
        override fun areItemsTheSame(oldItem: RecipeResult, newItem: RecipeResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecipeResult, newItem: RecipeResult): Boolean {
            return oldItem == newItem
        }
    }
}

class FoodViewHolder(
    private val binding: FoodItemBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: RecipeResult) {
        binding.food = item
        binding.executePendingBindings()
    }

    companion object{
        fun from(parent: ViewGroup): FoodViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FoodItemBinding.inflate(layoutInflater, parent, false)
            return FoodViewHolder(binding)
        }
    }
}