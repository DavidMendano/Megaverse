package com.dmendano.domain.usecase

import com.dmendano.domain.repository.GoalRepository

class GetGoalUseCase(
    private val goalRepository: GoalRepository
) {

    suspend operator fun invoke() {
        goalRepository.getGoal()
    }
}
