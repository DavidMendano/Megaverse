package com.dmendano.domain.model

sealed class MegaverseObjectDTO(val row: Int, val column: Int) {
    data class Space(val x: Int, val y: Int) : MegaverseObjectDTO(x, y)
    data class Polyanet(val x: Int, val y: Int) : MegaverseObjectDTO(x, y)
}
