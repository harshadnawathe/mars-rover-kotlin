package com.github.harshadnawathe.mars

import com.github.harshadnawathe.mars.Direction.*
import org.junit.Assert.assertEquals
import org.junit.Test

class PositionTest {

    @Test
    fun shouldIncrementCoordinatesOnCallToNext() {
        with(Position(Location(40, 40), EAST))
                .nextLocationShouldBe(Location(41,40))
        with(Position(Location(40, 40), SOUTH))
                .nextLocationShouldBe(Location(40,39))
        with(Position(Location(40, 40), WEST))
                .nextLocationShouldBe(Location(39,40))
        with(Position(Location(40, 40), NORTH))
                .nextLocationShouldBe(Location(40,41))


    }

    class Tester(private val position: Position) {
        fun nextLocationShouldBe(expectedLocation: Location) {
            assertEquals(expectedLocation, position.next().location)
        }
    }

    private fun with(position: Position) = Tester(position)
}