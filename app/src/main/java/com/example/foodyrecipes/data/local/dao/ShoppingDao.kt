package com.example.foodyrecipes.data.local.dao

import androidx.room.*
import com.example.foodyrecipes.data.local.entity.ShoppingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shoppingEntity: ShoppingEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(shoppingEntity: ShoppingEntity)

    @Delete
    suspend fun delete(shoppingEntity: ShoppingEntity)

    @Query("SELECT * FROM shopping_table ORDER BY id DESC")
    fun getShoppingList(): Flow<List<ShoppingEntity>>
}