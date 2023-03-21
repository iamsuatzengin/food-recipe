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
            delay(3000)
            try {
                val api = apiService.getRecipes(queries)
                if (api.isSuccessful) {
                    emit(NetworkResult.Success(data = api.body()))
                } else {
                    emit(NetworkResult.Error("Bilinmeyen bir hata! ${api.code()}"))
                }
            } catch (e: Exception) {
                emit(resolveError(exception = e))
            }
        }.flowOn(ioDispatchers)
    }
}

private fun resolveError(exception: Exception): NetworkResult.Error {

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