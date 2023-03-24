package com.example.foodyrecipes.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import com.example.foodyrecipes.databinding.FavoriteItemBinding

class FavoritesAdapter : ListAdapter<FavoriteEntity, FavoritesViewHolder>(DiffUtilCallback) {

    private var onClickListener: ((FavoriteEntity, View) -> Unit)? = null

    fun setOnItemClickListener(onClick: (FavoriteEntity, View) -> Unit) {
        onClickListener = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<FavoriteEntity>() {
        override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem == newItem
        }
    }
}


class FavoritesViewHolder(
    private val binding: FavoriteItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: FavoriteEntity, onClickListener: ((FavoriteEntity, View) -> Unit)?) {
        binding.food = item
        binding.cardView.setOnClickListener {
            onClickListener?.invoke(item, binding.ivFood)
        }
        binding.executePendingBindings()
    }


    companion object {
        fun from(parent: ViewGroup): FavoritesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FavoriteItemBinding.inflate(layoutInflater, parent, false)
            return FavoritesViewHolder(binding)
        }
    }
}
