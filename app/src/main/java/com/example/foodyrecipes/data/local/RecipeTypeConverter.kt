package com.example.foodyrecipes.data.local

import androidx.room.TypeConverter
import com.example.foodyrecipes.data.network.dto.RecipeResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RecipeTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun recipeResultToString(recipeResult: RecipeResult): String {
        return gson.toJson(recipeResult)
    }

    @TypeConverter
    fun stringToRecipeResult(json: String): RecipeResult {
        val listType = object : TypeToken<RecipeResult>() {}.type
        return gson.fromJson(json, listType)
    }

}