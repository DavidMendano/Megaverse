package com.dmendano.domain.usecase

import com.dmendano.domain.model.MegaverseObjectDTO
import com.dmendano.domain.repository.GoalRepository

class CreateSoloonUseCase(
    private val goalRepository: GoalRepository,
) {
    suspend operator fun invoke(megaverseObject: MegaverseObjectDTO.Soloon): Result<Unit> =
        goalRepository.createSoloon(megaverseObject)
}
