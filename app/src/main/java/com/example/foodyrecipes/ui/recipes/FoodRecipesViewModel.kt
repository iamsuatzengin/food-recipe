package com.example.foodyrecipes.ui.recipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodyrecipes.data.data_store.DataStoreRepository
import com.example.foodyrecipes.data.network.dto.FoodRecipe
import com.example.foodyrecipes.data.repository.FoodRecipeRepository
import com.example.foodyrecipes.di.IoDispatchers
import com.example.foodyrecipes.util.Constants
import com.example.foodyrecipes.util.Constants.QUERY_SEARCH
import com.example.foodyrecipes.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodRecipesViewModel @Inject constructor(
    private val foodRecipeRepository: FoodRecipeRepository,
    private val dataStoreRepository: DataStoreRepository,
    @IoDispatchers private val ioDispatchers: CoroutineDispatcher
) : ViewModel() {

    private val _state: MutableStateFlow<NetworkResult<FoodRecipe>> =
        MutableStateFlow(NetworkResult.Loading)
    val state: StateFlow<NetworkResult<FoodRecipe>>
        get() = _state

    private val _mealAndDietTypeState = MutableStateFlow(MealAndDietTypeState())
    val mealAndDietTypeState: StateFlow<MealAndDietTypeState>
        get() = _mealAndDietTypeState

    private val _searchedRecipeState: MutableStateFlow<NetworkResult<FoodRecipe>> =
        MutableStateFlow(NetworkResult.Loading)
    val searchedRecipeState: StateFlow<NetworkResult<FoodRecipe>>
        get() = _searchedRecipeState

    val networkStatus = MutableLiveData(false)

    init {
        getMealAndDietType()
    }

    fun getRecipes() {
        foodRecipeRepository.getRecipes(applyQueries()).onEach { result ->
            _state.value = result
        }.launchIn(viewModelScope)
    }

    fun searchRecipes(query: String) {
        foodRecipeRepository.searchRecipes(applySearchQuery(query = query)).onEach { result ->
            _searchedRecipeState.value = result
        }.launchIn(viewModelScope)
    }


    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        viewModelScope.launch(ioDispatchers) {
            dataStoreRepository.saveMealAndDietType(
                mealType = mealType,
                mealTypeId = mealTypeId,
                dietType = dietType,
                dietTypeId = dietTypeId
            )
        }
    }

    private fun getMealAndDietType() {
        dataStoreRepository.getMealAndDietType.onEach { mealAndDietType ->
            _mealAndDietTypeState.value = MealAndDietTypeState(
                mealAndDietType.selectedMealType,
                mealAndDietType.selectedMealTypeId,
                mealAndDietType.selectedDietType,
                mealAndDietType.selectedDietTypeId
            )
        }.launchIn(viewModelScope)
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        getMealAndDietType()

        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = _mealAndDietTypeState.value.mealType
        queries[Constants.QUERY_DIET] = _mealAndDietTypeState.value.dietType
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    private fun applySearchQuery(query: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = query
        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

}

data class MealAndDietTypeState(
    val mealType: String = Constants.DEFAULT_MEAL_TYPE,
    val mealTypeId: Int = 0,
    val dietType: String = Constants.DEFAULT_DIET_TYPE,
    val dietTypeId: Int = 0
)