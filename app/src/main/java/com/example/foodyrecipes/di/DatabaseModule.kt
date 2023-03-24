package com.example.foodyrecipes.di

import android.content.Context
import androidx.room.Room
import com.example.foodyrecipes.data.local.FavoriteDao
import com.example.foodyrecipes.data.local.RecipeDatabase
import com.example.foodyrecipes.util.Constants.RECIPE_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFavoriteDao(db: RecipeDatabase): FavoriteDao = db.favoriteDao()

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            RECIPE_DB_NAME
        ).build()
    }
}