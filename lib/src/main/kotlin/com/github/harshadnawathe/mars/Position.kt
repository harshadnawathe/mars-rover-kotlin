package com.github.harshadnawathe.mars

import com.github.harshadnawathe.mars.Direction.*

data class Position(val location: Location, val orientation: Direction) {
    constructor() : this(Location(), NORTH)

    fun next() = when (orientation) {
        NORTH -> Position(Location(location.x, location.y + 1), orientation)
        EAST -> Position(Location(location.x + 1, location.y), orientation)
        SOUTH -> Position(Location(location.x, location.y - 1), orientation)
        WEST -> Position(Location(location.x - 1, location.y), orientation)
    }
}