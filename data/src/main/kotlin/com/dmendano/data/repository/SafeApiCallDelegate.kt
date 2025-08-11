package com.dmendano.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface SafeApiCallDelegate {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): Result<T>
}

class SafeApiCallDelegateImpl : SafeApiCallDelegate {
    private companion object {
        private const val MAX_RETRIES = 5
        private const val INITIAL_RETRY_DELAY_MS = 1000L // 1 second
    }

    override suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): Result<T> = withContext(dispatcher) {
        var currentRetry = 0
        var delayMs = INITIAL_RETRY_DELAY_MS

        while (currentRetry <= MAX_RETRIES) {
            try {
                val result = apiCall()
                return@withContext Result.success(result)
            } catch (e: Exception) {
                when {
                    e is HttpException && e.code() == 429 -> {
                        if (currentRetry++ < MAX_RETRIES) {
                            // Exponential backoff: 1s, 2s, 4s, etc.
                            delay(delayMs)
                            delayMs *= 2
                            continue
                        }
                        return@withContext Result.failure(
                            Exception("Server is busy. Please try again later.")
                        )
                    }

                    e is HttpException && e.code() in 500..599 -> {
                        // Server error, might be temporary
                        if (currentRetry++ < MAX_RETRIES) {
                            delay(delayMs)
                            delayMs *= 2
                            continue
                        }
                        return@withContext Result.failure(
                            Exception("Server error. This error is temporary.")
                        )
                    }

                    else -> {
                        // For other errors, don't retry
                        return@withContext Result.failure(
                            e.message?.let { Exception(it) }
                                ?: Exception("An unknown error occurred")
                        )
                    }
                }
            }
        }

        // This should theoretically never be reached due to the while loop logic
        Result.failure(Exception("Request failed after $MAX_RETRIES retries"))
    }
}