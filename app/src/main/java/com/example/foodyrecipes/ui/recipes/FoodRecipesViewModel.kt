package com.example.foodyrecipes.ui.recipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodyrecipes.data.repository.FoodRecipeRepository
import com.example.foodyrecipes.util.Constants
import com.example.foodyrecipes.util.NetworkResult
import com.example.foodyrecipes.data.network.dto.FoodRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FoodRecipesViewModel @Inject constructor(
    private val foodRecipeRepository: FoodRecipeRepository
) : ViewModel() {

    private val _state: MutableStateFlow<NetworkResult<FoodRecipe>> =
        MutableStateFlow(NetworkResult.Loading)
    val state: StateFlow<NetworkResult<FoodRecipe>>
        get() = _state

    val networkStatus = MutableLiveData(false)

    fun getRecipes() {
        foodRecipeRepository.getRecipes(applyQueries()).onEach { result ->
            _state.value = result
        }.launchIn(viewModelScope)
    }


    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()


        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = Constants.DEFAULT_MEAL_TYPE
        queries[Constants.QUERY_DIET] = Constants.DEFAULT_DIET_TYPE
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}