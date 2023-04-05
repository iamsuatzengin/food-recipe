package com.example.foodyrecipes.repository

import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun addFavoriteRecipe(favoriteEntity: FavoriteEntity)

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    fun getFavorites(): Flow<List<FavoriteEntity>>

    suspend fun deleteAllFavorites()
}