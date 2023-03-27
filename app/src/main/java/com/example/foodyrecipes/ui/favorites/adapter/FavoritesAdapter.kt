package com.example.foodyrecipes.ui.favorites.adapter

import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.foodyrecipes.R
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import com.example.foodyrecipes.ui.favorites.FavoritesFragmentDirections
import com.google.android.material.card.MaterialCardView

class FavoritesAdapter(
    private val requireActivity: FragmentActivity
) : ListAdapter<FavoriteEntity, FavoritesViewHolder>(DiffUtilCallback) {

    private lateinit var actionMode: ActionMode

    private var multiSelection = false
    private var selectedFoods = arrayListOf<FavoriteEntity>()
    private var viewHolders = arrayListOf<FavoritesViewHolder>()

    private var onClickDeleteFood: ((FavoriteEntity, Int) -> Unit)? = null

    fun setOnClickDeleteFood(onClick: (FavoriteEntity, Int) -> Unit) {
        onClickDeleteFood = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        viewHolders.add(holder)

        val item = getItem(position)
        holder.bind(item)

        val cardLayout = holder.itemView.findViewById<MaterialCardView>(R.id.cardView)

        cardLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, item)
            } else {
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(item.recipeResult)
                val extras = holder.navigatorExtras(item)
                holder.itemView.findNavController().navigate(action, extras)
            }
        }

        cardLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(actionModeCallback)
                applySelection(holder, item)
                true
            } else {
                multiSelection = false
                false
            }
        }
    }

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
            actionMode?.menuInflater?.inflate(R.menu.favorites_conextual_menu, menu)
            this@FavoritesAdapter.actionMode = actionMode!!
            return true
        }

        override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
            if (menu?.itemId == R.id.delete_favorite) {
                selectedFoods.forEach {
                    println("deleted: ${it.recipeResult.title}")
                    onClickDeleteFood?.invoke(it, selectedFoods.size)
                }

                multiSelection = false
                selectedFoods.clear()
                actionMode?.finish()
            }

            return true
        }

        override fun onDestroyActionMode(actionMode: ActionMode?) {
            viewHolders.forEach { holder ->
                holder.changeFoodStyle(View.INVISIBLE)
            }
            multiSelection = false
            selectedFoods.clear()
        }
    }

    private fun applyActionBarTitle() {
        if (selectedFoods.size == 0) {
            actionMode.finish()
            return
        }
        actionMode.title = "${selectedFoods.size} item selected!"
    }

    private fun applySelection(holder: FavoritesViewHolder, currentFood: FavoriteEntity) {
        if (selectedFoods.contains(currentFood)) {
            selectedFoods.remove(currentFood)
            holder.changeFoodStyle(View.INVISIBLE)
            applyActionBarTitle()
        } else {
            selectedFoods.add(currentFood)
            holder.changeFoodStyle(View.VISIBLE)
            applyActionBarTitle()
        }
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

