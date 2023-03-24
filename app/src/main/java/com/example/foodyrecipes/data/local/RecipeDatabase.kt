package com.example.foodyrecipes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodyrecipes.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase: RoomDatabase(){

    abstract fun favoriteDao(): FavoriteDao
}