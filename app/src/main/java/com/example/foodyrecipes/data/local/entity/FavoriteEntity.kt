package com.example.foodyrecipes.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodyrecipes.data.network.dto.RecipeResult
import com.example.foodyrecipes.util.Constants.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val recipeResult: RecipeResult,
)
