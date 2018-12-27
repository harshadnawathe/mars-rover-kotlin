package com.github.harshadnawathe.mars

import org.junit.Assert.assertEquals
import org.junit.Test

class PlateauTest{
    @Test
    fun shouldReturnTrueIfCoordinatesAreWithinBounds() {
        val plateau = Plateau(Location(100, 100))
        assertEquals(true, plateau.isWithinBounds(Location(50,75)))
    }

    @Test
    fun shouldReturnFalseIfCoordinatesAreNotWithinBounds() {
        val plateau = Plateau(Location(100, 100))
        assertEquals(false, plateau.isWithinBounds(Location(150,75)))
    }
}