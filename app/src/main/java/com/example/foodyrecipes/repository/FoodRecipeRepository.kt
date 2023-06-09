package com.example.foodyrecipes.repository

import com.example.foodyrecipes.util.NetworkResult
import com.example.foodyrecipes.data.network.dto.FoodRecipe
import kotlinx.coroutines.flow.Flow

interface FoodRecipeRepository {

    fun getRecipes(queries: Map<String, String>): Flow<NetworkResult<FoodRecipe>>

    fun searchRecipes(searchQuery: Map<String, String>): Flow<NetworkResult<FoodRecipe>>
}