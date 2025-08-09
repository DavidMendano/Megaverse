package com.dmendano.data.apiservice

import com.dmendano.data.apiservice.model.GoalApiResponse
import com.dmendano.data.apiservice.model.PolyanetRequest
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MegaverseService {
    @GET("map/{candidateId}/goal")
    suspend fun getGoal(@Path("candidateId") candidateId: String): GoalApiResponse

    @POST("polyanets")
    suspend fun createPolyanets(
        @Body polyanet: PolyanetRequest,
    )

    @DELETE("polyanets")
    suspend fun deletePolyanets(
        @Query("candidateId") candidateId: String,
        @Query("row") row: Int,
        @Query("column") column: Int,
    ): ResponseBody
}
