package com.dmendano.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
            Result.failure(e)
        }
}
