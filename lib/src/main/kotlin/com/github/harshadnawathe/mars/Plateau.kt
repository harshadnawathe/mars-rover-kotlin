package com.github.harshadnawathe.mars

data class Plateau(
        val lowerLeft: Location,
        val topRight: Location
) {
    constructor(topRight: Location) : this(Location(0, 0), topRight)

    fun isWithinBounds(location: Location): Boolean {
        val xRange = IntRange(lowerLeft.x, topRight.x)
        val yRange = IntRange(lowerLeft.y, topRight.y)
        return xRange.contains(location.x) && yRange.contains(location.y)
    }
}