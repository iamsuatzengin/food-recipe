package com.example.foodyrecipes.ui.shopping_list.adapter


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.foodyrecipes.data.local.entity.ShoppingEntity

class ShoppingListAdapter: ListAdapter<ShoppingEntity, ShoppingListViewHolder>(DiffCallback) {

    private var onClickListener: ((ShoppingEntity, View) -> Unit)? = null

    fun setOnClickListener(onClick: (ShoppingEntity, View) -> Unit){
        onClickListener = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        return ShoppingListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<ShoppingEntity>(){
        override fun areItemsTheSame(oldItem: ShoppingEntity, newItem: ShoppingEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingEntity, newItem: ShoppingEntity): Boolean {
            return oldItem == newItem
        }

    }
}
