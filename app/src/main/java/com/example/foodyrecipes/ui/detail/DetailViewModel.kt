package com.example.foodyrecipes.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import com.example.foodyrecipes.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> get() = _eventFlow

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

    fun deleteFavoriteRecipe(entity: FavoriteEntity){
        viewModelScope.launch {
            try {
                favoriteRepository.deleteFavoriteRecipe(favoriteEntity = entity)
                _eventFlow.emit(UiEvent.ShowMessage(message = "Deleted successfully!"))
            }catch (e: Exception){
                _eventFlow.emit(UiEvent.ShowMessage(message = "Failed!"))
                Log.e("DB_ERROR", e.message.toString())
            }
        }
    }


}
sealed class UiEvent {
    class ShowMessage(val message: String): UiEvent()
}