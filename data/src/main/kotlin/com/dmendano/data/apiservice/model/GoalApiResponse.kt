package com.dmendano.data.apiservice.model

import com.dmendano.domain.model.GoalDTO
import com.dmendano.domain.model.MegaverseObjectDTO
import com.dmendano.domain.model.MegaverseOptions
import com.dmendano.domain.model.toColor
import com.dmendano.domain.model.toDirection
import com.dmendano.domain.model.toMegaverseOption
import com.google.gson.annotations.SerializedName

data class GoalApiResponse(
    @SerializedName("goal")
    val goal: List<List<String>>? = null
)

fun GoalApiResponse.mapToDto(): GoalDTO {
    val grid = this.goal?.mapIndexed { rowIdx, row ->
        row.mapIndexed { colIdx, value ->
            when (value.toMegaverseOption()) {
                MegaverseOptions.POLYANET -> MegaverseObjectDTO.Polyanet(rowIdx, colIdx)

                MegaverseOptions.COMETH -> MegaverseObjectDTO.Cometh(
                    value.toDirection(),
                    rowIdx,
                    colIdx
                )

                MegaverseOptions.SOLOON -> MegaverseObjectDTO.Soloon(
                    value.toColor(),
                    rowIdx,
                    colIdx
                )

                else -> MegaverseObjectDTO.Space(rowIdx, colIdx)
            }
        }
    }
    return GoalDTO(grid)
}
