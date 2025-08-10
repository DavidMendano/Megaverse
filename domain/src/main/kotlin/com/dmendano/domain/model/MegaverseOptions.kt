package com.dmendano.domain.model


enum class MegaverseOptions {
    SPACE,
    POLYANET,
    COMETH,
    SOLOON
}

enum class MegaverseDirection {
    RIGHT, LEFT, UP, DOWN
}

enum class MegaverseColor {
    WHITE, BLUE, PURPLE, RED
}

fun String.toMegaverseOption() =
    MegaverseOptions.valueOf(this.substringAfter("_").uppercase())

fun String.toDirection() =
    MegaverseDirection.valueOf(this.substringBefore("_").uppercase())

fun String.toColor() =
    MegaverseColor.valueOf(this.substringBefore("_").uppercase())
