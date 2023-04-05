package com.example.foodyrecipes.ui.shopping_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodyrecipes.data.local.entity.ShoppingEntity
import com.example.foodyrecipes.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
): ViewModel() {

    val state = repository.getShoppingList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun insert(title: String, myShoppingList: String){
        viewModelScope.launch {
            val entity = ShoppingEntity(
                title = title,
                shoppingList = myShoppingList,
            )
            repository.insert(shoppingEntity = entity)
        }
    }

    fun update(title: String, myShoppingList: String){
        viewModelScope.launch {
            val entity = ShoppingEntity(
                title = title,
                shoppingList = myShoppingList
            )
            repository.update(shoppingEntity = entity)
        }
    }

    fun delete(entity: ShoppingEntity){
        viewModelScope.launch {
            repository.delete(shoppingEntity = entity)
        }
    }

}