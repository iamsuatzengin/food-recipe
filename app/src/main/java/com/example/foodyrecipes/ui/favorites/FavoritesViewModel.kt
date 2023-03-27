package com.example.foodyrecipes.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import com.example.foodyrecipes.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    val favorites = favoriteRepository.getFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity){
        viewModelScope.launch {
            favoriteRepository.deleteFavoriteRecipe(favoriteEntity = favoriteEntity)
        }
    }
}