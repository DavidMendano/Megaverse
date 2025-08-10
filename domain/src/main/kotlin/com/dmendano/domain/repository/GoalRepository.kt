package com.dmendano.domain.repository

import com.dmendano.domain.model.GoalDTO
import com.dmendano.domain.model.MegaverseObjectDTO
import com.dmendano.domain.model.MegaverseOptions

interface GoalRepository {
    suspend fun getGoal(): Result<GoalDTO>

    suspend fun createPolyanet(megaverseObject: MegaverseObjectDTO.Polyanet): Result<Unit>

    suspend fun createCometh(megaverseObject: MegaverseObjectDTO.Cometh): Result<Unit>

    suspend fun createSoloon(megaverseObject: MegaverseObjectDTO.Soloon): Result<Unit>

    suspend fun delete(row: Int, column: Int, megaverseOption: MegaverseOptions): Result<Unit>
}
