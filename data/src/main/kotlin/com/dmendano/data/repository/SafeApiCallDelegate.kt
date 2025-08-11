package com.dmendano.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface SafeApiCallDelegate {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): Result<T>
}

class SafeApiCallDelegateImpl : SafeApiCallDelegate {

    override suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): Result<T> =
        try {
            withContext(dispatcher) {
                val result = apiCall()
                Result.success(result)
            }
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 429) {
                Result.failure(Exception("Too many requests"))
            } else {
                e.printStackTrace()
                Result.failure(Exception("Connection error"))
            }
        }
}
