package com.example.foodyrecipes.ui.shopping_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyrecipes.data.local.entity.ShoppingEntity
import com.example.foodyrecipes.databinding.ShoppingListItemBinding


class ShoppingListViewHolder(
    private val binding: ShoppingListItemBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: ShoppingEntity, onClickListener: ((ShoppingEntity, View) -> Unit)?) {
        binding.item = item
        binding.itemLayout.setOnClickListener {
            onClickListener?.invoke(item, binding.itemLayout)
        }
        binding.itemLayout.transitionName = item.id.toString()
        binding.executePendingBindings()

    }


    companion object{
        fun from(parent: ViewGroup): ShoppingListViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ShoppingListItemBinding.inflate(layoutInflater, parent, false)
            return ShoppingListViewHolder(binding)
        }
    }
}