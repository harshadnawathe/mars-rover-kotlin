package com.github.harshadnawathe.mars

typealias LocationValidator = (Location) -> Boolean

class Rover(initialPosition: Position,
            private val locationValidator: LocationValidator) {
    constructor(initialPosition: Position) : this(initialPosition, { true })
    constructor() : this(Position(), { true })

    var position: Position = initialPosition
        private set

    fun move(): Boolean {
        val nextPosition = position.next()
        if (isValid(nextPosition.location)) {
            position = nextPosition
            return true
        }
        return false
    }

    fun turnLeft() {
        position = Position(position.location, position.orientation.onLeft())
    }

    fun turnRight() {
        position = Position(position.location, position.orientation.onRight())
    }

    private fun isValid(location: Location) = locationValidator(location)
}
