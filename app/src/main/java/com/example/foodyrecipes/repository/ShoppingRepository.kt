package com.example.foodyrecipes.repository

import com.example.foodyrecipes.data.local.entity.ShoppingEntity
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {

    suspend fun insert(shoppingEntity: ShoppingEntity)

    suspend fun update(shoppingEntity: ShoppingEntity)

    suspend fun delete(shoppingEntity: ShoppingEntity)

    fun getShoppingList(): Flow<List<ShoppingEntity>>
}