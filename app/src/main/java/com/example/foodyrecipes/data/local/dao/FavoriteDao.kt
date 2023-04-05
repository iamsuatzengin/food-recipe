package com.example.foodyrecipes.data.local.dao

import androidx.room.*
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_table ORDER BY id ASC")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAllFavorites()
}