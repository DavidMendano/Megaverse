package com.dmendano.domain.usecase

import com.dmendano.domain.model.MegaverseObjectDTO
import com.dmendano.domain.repository.GoalRepository

class CreateComethUseCase(
    private val goalRepository: GoalRepository,
) {
    suspend operator fun invoke(megaverseObject: MegaverseObjectDTO.Cometh): Result<Unit> =
        goalRepository.createCometh(megaverseObject)
}
