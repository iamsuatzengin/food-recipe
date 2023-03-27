package com.example.foodyrecipes.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import com.example.foodyrecipes.databinding.FavoriteItemBinding

class FavoritesViewHolder(
    private val binding: FavoriteItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: FavoriteEntity) {
        binding.food = item
        binding.executePendingBindings()
    }

    fun changeFoodStyle(visibilty: Int) {
        binding.iconItemSelected.visibility = visibilty
    }

    fun navigatorExtras(item: FavoriteEntity): FragmentNavigator.Extras {
        return FragmentNavigatorExtras(
            binding.ivFood to item.recipeResult.image
        )
    }

    companion object {
        fun from(parent: ViewGroup): FavoritesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FavoriteItemBinding.inflate(layoutInflater, parent, false)
            return FavoritesViewHolder(binding)
        }
    }
}
