package com.dmendano.data.repository

import com.dmendano.data.apiservice.MegaverseService
import com.dmendano.data.apiservice.model.ComethRequest
import com.dmendano.data.apiservice.model.DeleteRequest
import com.dmendano.data.apiservice.model.PolyanetRequest
import com.dmendano.data.apiservice.model.SoloonRequest
import com.dmendano.data.apiservice.model.mapToDto
import com.dmendano.domain.model.GoalDTO
import com.dmendano.domain.model.MegaverseObjectDTO
import com.dmendano.domain.model.MegaverseOptions
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

    override suspend fun createPolyanet(megaverseObject: MegaverseObjectDTO.Polyanet): Result<Unit> =
        safeApiCall {
            val request = PolyanetRequest(
                candidateId = candidateId,
                row = megaverseObject.row,
                column = megaverseObject.column
            )
            megaverseService.createPolyanets(request)
        }

    override suspend fun createCometh(megaverseObject: MegaverseObjectDTO.Cometh): Result<Unit> =
        safeApiCall {
            val request = ComethRequest(
                candidateId = candidateId,
                direction = megaverseObject.direction.name.lowercase(),
                row = megaverseObject.row,
                column = megaverseObject.column
            )
            megaverseService.createComeths(request)
        }

    override suspend fun createSoloon(megaverseObject: MegaverseObjectDTO.Soloon): Result<Unit> =
        safeApiCall {
            val request = SoloonRequest(
                candidateId = candidateId,
                color = megaverseObject.color.name.lowercase(),
                row = megaverseObject.row,
                column = megaverseObject.column
            )
            megaverseService.createSoloons(request)
        }

    override suspend fun delete(
        row: Int,
        column: Int,
        megaverseOption: MegaverseOptions
    ): Result<Unit> =
        safeApiCall {
            val request = DeleteRequest(
                candidateId = candidateId,
                row = row,
                column = column
            )
            when (megaverseOption) {
                MegaverseOptions.COMETH -> megaverseService.deleteComeths(request)
                MegaverseOptions.SOLOON -> megaverseService.deleteSoloons(request)
                else -> megaverseService.deletePolyanets(request)
            }
        }
}
