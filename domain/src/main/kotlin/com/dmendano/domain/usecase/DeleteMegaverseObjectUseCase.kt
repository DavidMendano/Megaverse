package com.dmendano.domain.usecase

import com.dmendano.domain.model.MegaverseOptions
import com.dmendano.domain.repository.GoalRepository

class DeleteMegaverseObjectUseCase(
    private val goalRepository: GoalRepository,
) {
    suspend operator fun invoke(
        row: Int,
        column: Int,
        megaverseOption: MegaverseOptions
    ): Result<Unit> =
        goalRepository.delete(row, column, megaverseOption)
}
