package com.dmendano.data.apiservice.model

import com.dmendano.domain.model.GoalDTO
import com.dmendano.domain.model.MegaverseObjectDTO
import com.google.gson.annotations.SerializedName

data class GoalApiResponse(
    @SerializedName("goal")
    val goal: List<List<String>>? = null
)

fun GoalApiResponse.mapToDto(): GoalDTO {
    val grid = this.goal?.mapIndexed { rowIdx, row ->
        row.mapIndexed { colIdx, value ->
            when (value) {
                MegaverseOptions.POLYANET.value -> MegaverseObjectDTO.Polyanet(rowIdx, colIdx)
                else -> MegaverseObjectDTO.Space(rowIdx, colIdx)
            }
        }
    }
    return GoalDTO(grid)
}
