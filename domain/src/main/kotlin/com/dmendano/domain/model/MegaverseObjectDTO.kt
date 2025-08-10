package com.dmendano.domain.model

sealed class MegaverseObjectDTO(val row: Int, val column: Int) {
    data class Space(
        val x: Int,
        val y: Int
    ) : MegaverseObjectDTO(x, y) {
        override fun toString(): String =
            "Space: x:$x, y:$y"
    }

    data class Polyanet(
        val x: Int,
        val y: Int
    ) : MegaverseObjectDTO(x, y) {
        override fun toString(): String =
            "Polyanet: x:$x, y:$y"
    }

    data class Cometh(
        val direction: MegaverseDirection,
        val x: Int,
        val y: Int
    ) : MegaverseObjectDTO(x, y) {
        override fun toString(): String =
            "Cometh: direction:$direction - x:$x, y:$y"
    }

    data class Soloon(
        val color: MegaverseColor,
        val x: Int,
        val y: Int
    ) : MegaverseObjectDTO(x, y) {
        override fun toString(): String =
            "Soloon: direction:$color - x:$x, y:$y"
    }
}
