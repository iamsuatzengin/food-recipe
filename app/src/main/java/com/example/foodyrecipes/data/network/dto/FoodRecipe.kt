package com.example.foodyrecipes.data.network.dto


import com.google.gson.annotations.SerializedName

data class FoodRecipe(
    @SerializedName("results")
    val results: List<RecipeResult>
)