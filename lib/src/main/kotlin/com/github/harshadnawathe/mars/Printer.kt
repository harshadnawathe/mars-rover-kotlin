package com.github.harshadnawathe.mars

fun Position.print() {
    val directionText = when (this.orientation) {
        Direction.NORTH -> "N"
        Direction.EAST -> "E"
        Direction.SOUTH -> "S"
        Direction.WEST -> "W"
    }

    println("${this.location.x} ${this.location.y} $directionText")
}

