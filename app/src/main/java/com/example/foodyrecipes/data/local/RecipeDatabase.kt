package com.example.foodyrecipes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodyrecipes.data.local.dao.FavoriteDao
import com.example.foodyrecipes.data.local.dao.ShoppingDao
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import com.example.foodyrecipes.data.local.entity.ShoppingEntity

@Database(entities = [FavoriteEntity::class, ShoppingEntity::class], version = 2, exportSchema = false)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase: RoomDatabase(){

    abstract fun favoriteDao(): FavoriteDao

    abstract fun shoppingDao(): ShoppingDao
}