package com.dmendano.data.apiservice

import com.dmendano.data.apiservice.model.GoalApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MegaverseService {
    @GET("map/{candidateId}/goal")
    suspend fun getGoal(@Path("candidateId") candidateId: String): GoalApiResponse
}
