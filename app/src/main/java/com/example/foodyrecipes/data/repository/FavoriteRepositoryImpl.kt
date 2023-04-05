package com.example.foodyrecipes.data.repository

import android.util.Log
import com.example.foodyrecipes.data.local.dao.FavoriteDao
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import com.example.foodyrecipes.di.IoDispatchers
import com.example.foodyrecipes.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao,
    @IoDispatchers private val ioDispatchers: CoroutineDispatcher
) : FavoriteRepository {
    override suspend fun addFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        return withContext(ioDispatchers) {
            dao.addFavoriteRecipe(favoriteEntity = favoriteEntity)
        }
    }

    override suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        return withContext(ioDispatchers) {
            dao.deleteFavoriteRecipe(favoriteEntity = favoriteEntity)
        }
    }

    override fun getFavorites(): Flow<List<FavoriteEntity>> {
        return dao.getFavorites()
            .catch { e ->
                Log.e("DB_ERROR", "error: ${e.message}")
            }.flowOn(ioDispatchers)
    }

    override suspend fun deleteAllFavorites() = withContext(ioDispatchers) {
        dao.deleteAllFavorites()
    }
}