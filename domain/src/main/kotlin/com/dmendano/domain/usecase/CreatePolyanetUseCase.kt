package com.dmendano.domain.usecase

import com.dmendano.domain.model.MegaverseObjectDTO
import com.dmendano.domain.repository.GoalRepository

class CreatePolyanetUseCase(
    private val goalRepository: GoalRepository,
) {
    suspend operator fun invoke(megaverseObject: MegaverseObjectDTO): Result<Unit> =
        goalRepository.createPolyanet(megaverseObject)
}
