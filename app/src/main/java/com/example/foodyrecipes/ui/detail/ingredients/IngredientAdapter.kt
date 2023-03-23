package com.example.foodyrecipes.ui.detail.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodyrecipes.data.network.dto.ExtendedIngredient
import com.example.foodyrecipes.databinding.IngredientItemBinding

class IngredientAdapter(
    private val list: List<ExtendedIngredient>
) : RecyclerView.Adapter<IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder.from(parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}

class IngredientViewHolder(
    private val binding: IngredientItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ExtendedIngredient) {
        val imageUrl = "https://spoonacular.com/cdn/ingredients_100x100/${item.image}"
        with(binding) {
            ivIngredient.load(imageUrl) {
                crossfade(600)
            }
            tvName.text = item.name
            tvOriginal.text = item.original
            tvConsistency.text = item.consistency
        }
    }


    companion object {
        fun from(parent: ViewGroup): IngredientViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = IngredientItemBinding.inflate(layoutInflater, parent, false)
            return IngredientViewHolder(binding)
        }
    }

}