package com.example.foodyrecipes.data.repository

import com.example.foodyrecipes.data.local.dao.ShoppingDao
import com.example.foodyrecipes.data.local.entity.ShoppingEntity
import com.example.foodyrecipes.di.IoDispatchers
import com.example.foodyrecipes.repository.ShoppingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao,
    @IoDispatchers private val ioDispatchers: CoroutineDispatcher
): ShoppingRepository {

    override suspend fun insert(shoppingEntity: ShoppingEntity) = withContext(ioDispatchers){
        shoppingDao.insert(shoppingEntity)
    }

    override suspend fun update(shoppingEntity: ShoppingEntity) = withContext(ioDispatchers){
        shoppingDao.update(shoppingEntity)
    }

    override suspend fun delete(shoppingEntity: ShoppingEntity) = withContext(ioDispatchers){
        shoppingDao.delete(shoppingEntity)
    }

    override fun getShoppingList(): Flow<List<ShoppingEntity>> {
        return shoppingDao.getShoppingList()
            .flowOn(ioDispatchers)
    }
}