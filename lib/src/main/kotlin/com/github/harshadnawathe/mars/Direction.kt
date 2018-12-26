package com.github.harshadnawathe.mars

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun onLeft() : Direction = when(this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
    }

    fun onRight() : Direction = when(this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}

