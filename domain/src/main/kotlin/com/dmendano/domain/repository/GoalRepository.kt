package com.dmendano.domain.repository

import com.dmendano.domain.model.GoalDTO
import com.dmendano.domain.model.MegaverseObjectDTO

interface GoalRepository {
    suspend fun getGoal(): Result<GoalDTO>

    suspend fun createPolyanet(megaverseObject: MegaverseObjectDTO): Result<Unit>
}
