package com.github.harshadnawathe.mars

import org.junit.Assert.*
import org.junit.Test

class RoverTest{

    @Test
    fun shouldMoveOneStep() {
        val rover = Rover()

        val success = rover.move()

        assertTrue(success)
        assertEquals(Position(Location(0,1), Direction.NORTH), rover.position)
    }

    @Test
    fun shouldNotMoveIfNextLocationIsInvalid() {
        val initialPosition = Position()
        val rover = Rover(initialPosition) { false }

        val success = rover.move()

        assertFalse(success)
        assertEquals(initialPosition, rover.position)
    }

    @Test
    fun shouldTurnLeft() {
        val rover = Rover()

        rover.turnLeft()

        assertEquals(Position(Location(0,0), Direction.WEST), rover.position)
    }

    @Test
    fun shouldTurnRight() {
        val rover = Rover()

        rover.turnRight()

        assertEquals(Position(Location(0,0), Direction.EAST), rover.position)
    }
}