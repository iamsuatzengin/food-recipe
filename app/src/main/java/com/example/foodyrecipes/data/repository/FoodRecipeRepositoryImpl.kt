package com.example.foodyrecipes.data.repository

import com.example.foodyrecipes.data.network.ApiService
import com.example.foodyrecipes.di.IoDispatchers
import com.example.foodyrecipes.util.NetworkResult
import com.example.foodyrecipes.data.network.dto.FoodRecipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class FoodRecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatchers private val ioDispatchers: CoroutineDispatcher
) : FoodRecipeRepository {
    override fun getRecipes(queries: Map<String, String>): Flow<NetworkResult<FoodRecipe>> {
        return flow {
            emit(NetworkResult.Loading)
            delay(1000)
            try {
                val api = apiService.getRecipes(queries = queries)
                if (!api.isSuccessful) {
                    throw HttpException(api)
                }
                emit(NetworkResult.Success(data = api.body()))
            } catch (e: Exception) {
                emit(resolveError(exception = e))
            }
        }.flowOn(ioDispatchers)
    }

    override fun searchRecipes(searchQuery: Map<String, String>): Flow<NetworkResult<FoodRecipe>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val search = apiService.searchRecipes(searchQuery = searchQuery)
                if(!search.isSuccessful){
                    throw HttpException(search)
                }
                emit(NetworkResult.Success(data = search.body()))
            }catch (e: Exception){
                emit(resolveError(exception = e))
            }
        }.flowOn(ioDispatchers)
    }
}


fun resolveError(exception: Exception): NetworkResult.Error {

    return when (exception) {
        is SocketTimeoutException -> {
            NetworkResult.Error(errorMessage = "Timeout")
        }
        is HttpException -> {
            if (exception.code() == 402) NetworkResult.Error("Api Key Limited")
            else NetworkResult.Error(errorMessage = "Bilinmeyen bir hata ${exception.message()}")
        }
        else -> {
            NetworkResult.Error(errorMessage = "Bilinmeyen bir hata! ${exception.message}")
        }
    }

}