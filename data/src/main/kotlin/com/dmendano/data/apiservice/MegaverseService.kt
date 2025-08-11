package com.dmendano.data.apiservice

import com.dmendano.data.apiservice.model.ComethRequest
import com.dmendano.data.apiservice.model.DeleteRequest
import com.dmendano.data.apiservice.model.GoalApiResponse
import com.dmendano.data.apiservice.model.PolyanetRequest
import com.dmendano.data.apiservice.model.SoloonRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface MegaverseService {
    @GET("map/{candidateId}/goal")
    suspend fun getGoal(@Path("candidateId") candidateId: String): GoalApiResponse

    @POST("polyanets")
    suspend fun createPolyanets(@Body polyanet: PolyanetRequest)

    @POST("comeths")
    suspend fun createComeths(@Body polyanet: ComethRequest)

    @POST("soloons")
    suspend fun createSoloons(@Body polyanet: SoloonRequest)

    @HTTP(method = "DELETE", path = "polyanets", hasBody = true)
    suspend fun deletePolyanets(@Body polyanet: DeleteRequest)

    @HTTP(method = "DELETE", path = "comeths", hasBody = true)
    suspend fun deleteComeths(@Body polyanet: DeleteRequest)

    @HTTP(method = "DELETE", path = "soloons", hasBody = true)
    suspend fun deleteSoloons(@Body polyanet: DeleteRequest)
}
