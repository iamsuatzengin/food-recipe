package com.example.foodyrecipes.di

import com.example.foodyrecipes.data.repository.FavoriteRepository
import com.example.foodyrecipes.data.repository.FavoriteRepositoryImpl
import com.example.foodyrecipes.data.repository.FoodRecipeRepository
import com.example.foodyrecipes.data.repository.FoodRecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFoodRecipeRepository(foodRecipeRepositoryImpl: FoodRecipeRepositoryImpl): FoodRecipeRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository
}