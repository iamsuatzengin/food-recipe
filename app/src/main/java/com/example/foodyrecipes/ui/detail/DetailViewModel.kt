package com.example.foodyrecipes.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import com.example.foodyrecipes.data.network.dto.RecipeResult
import com.example.foodyrecipes.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> get() = _eventFlow

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> get() = _isSaved

    private var savedRecipeId = 0

    fun addFavoriteRecipe(entity: FavoriteEntity){
        viewModelScope.launch {
            try {
                favoriteRepository.addFavoriteRecipe(favoriteEntity = entity)
                _eventFlow.emit(UiEvent.ShowMessage(message = "Added successfully!"))
            }catch (e: Exception){
                _eventFlow.emit(UiEvent.ShowMessage(message = "Failed!"))
                Log.e("DB_ERROR", e.message.toString())
            }
        }
    }

    fun deleteFavoriteRecipe(recipeResult: RecipeResult){
        viewModelScope.launch {
            try {
                val entity = FavoriteEntity(
                    id = savedRecipeId,
                    recipeResult = recipeResult
                )
                favoriteRepository.deleteFavoriteRecipe(favoriteEntity = entity)
                _eventFlow.emit(UiEvent.ShowMessage(message = "Deleted successfully!"))
            }catch (e: Exception){
                _eventFlow.emit(UiEvent.ShowMessage(message = "Failed!"))
                Log.e("DB_ERROR", e.message.toString())
            }
        }
    }

    fun foodIsSaved(id: Int){
        viewModelScope.launch {
            favoriteRepository.getFavorites()
                .collect{
                    it.forEach { entity ->
                        if(entity.recipeResult.id == id) {
                            println("kayıtlı")
                            _isSaved.value = true
                            savedRecipeId = entity.id
                            return@collect
                        }
                        _isSaved.value = false
                    }
                }
        }
    }

}
sealed class UiEvent {
    class ShowMessage(val message: String): UiEvent()
}