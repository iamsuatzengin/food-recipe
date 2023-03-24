package com.example.foodyrecipes.util

object Constants {

    const val BASE_URL = "https://api.spoonacular.com"

    const val API_KEY = "1648acdf5a264b9faf0cb81ca14f0b20"

    const val FOOD_PAGER_KEY = "FOOD_PAGER_KEY"

    // API Query Keys
    const val QUERY_NUMBER = "number"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_TYPE = "type"
    const val QUERY_DIET = "diet"
    const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
    const val QUERY_FILL_INGREDIENTS = "fillIngredients"
    const val QUERY_SEARCH = "query"


    const val DEFAULT_RECIPES_NUMBER = "50"
    const val DEFAULT_MEAL_TYPE = "main course"
    const val DEFAULT_DIET_TYPE = "gluten free"

    const val PREFERENCES_NAME = "foody_preferences"
    const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
    const val PREFERENCES_MEAL_TYPE = "mealType"
    const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
    const val PREFERENCES_DIET_TYPE = "dietType"

    // ROOM DB
    const val RECIPE_DB_NAME = "recipe_db"
    const val FAVORITE_TABLE = "favorite_table"
}