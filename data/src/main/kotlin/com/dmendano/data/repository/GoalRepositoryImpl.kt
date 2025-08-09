package com.dmendano.data.repository

import com.dmendano.data.apiservice.MegaverseService
import com.dmendano.domain.repository.GoalRepository

class GoalRepositoryImpl(
    private val candidateId: String,
    private val megaverseService: MegaverseService,
) : GoalRepository {

    override suspend fun getGoal() {
        megaverseService.getGoal(candidateId)
    }
}
