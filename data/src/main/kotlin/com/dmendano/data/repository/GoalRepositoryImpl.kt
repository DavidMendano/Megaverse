package com.dmendano.data.repository

import com.dmendano.data.apiservice.MegaverseService
import com.dmendano.data.apiservice.model.PolyanetRequest
import com.dmendano.data.apiservice.model.mapToDto
import com.dmendano.domain.model.GoalDTO
import com.dmendano.domain.model.MegaverseObjectDTO
import com.dmendano.domain.repository.GoalRepository

class GoalRepositoryImpl(
    safeApiCallDelegate: SafeApiCallDelegate,
    private val candidateId: String,
    private val megaverseService: MegaverseService,
) : GoalRepository, SafeApiCallDelegate by safeApiCallDelegate {

    override suspend fun getGoal(): Result<GoalDTO> =
        safeApiCall {
            megaverseService
                .getGoal(candidateId)
                .mapToDto()
        }

    override suspend fun createPolyanet(megaverseObject: MegaverseObjectDTO): Result<Unit> =
        safeApiCall {
            val request = PolyanetRequest(
                candidateId = candidateId,
                row = megaverseObject.row,
                column = megaverseObject.column
            )
            megaverseService.createPolyanets(request)
        }
}
