package com.dmendano.domain.usecase

import com.dmendano.domain.model.GoalDTO
import com.dmendano.domain.repository.GoalRepository

class GetGoalUseCase(
    private val goalRepository: GoalRepository
) {

    suspend operator fun invoke(): Result<GoalDTO> =
        goalRepository.getGoal()
}
