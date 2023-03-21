package com.example.foodyrecipes.data.repository

import com.example.foodyrecipes.util.NetworkResult
import com.example.foodyrecipes.data.network.dto.FoodRecipe
import kotlinx.coroutines.flow.Flow

interface FoodRecipeRepository {

    fun getRecipes(queries: Map<String, String>): Flow<NetworkResult<FoodRecipe>>
}