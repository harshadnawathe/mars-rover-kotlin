package com.github.harshadnawathe.mars

import com.github.harshadnawathe.mars.Command.*
import com.github.harshadnawathe.mars.Direction.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ExplorationPlanTest{

    @Test
    fun shouldExecuteExplorationPlan() {

        val explorationPlan = ExplorationPlan(Plateau(Location(5, 5)),
                arrayListOf(
                        Deployment(Position(Location(1, 2), NORTH),
                                arrayListOf(LEFT, MOVE, LEFT, MOVE, LEFT, MOVE, LEFT, MOVE, MOVE)),
                        Deployment(Position(Location(3, 3), EAST),
                                arrayListOf(MOVE, MOVE, RIGHT, MOVE, MOVE, RIGHT, MOVE, RIGHT, RIGHT, MOVE))))

        explorationPlan.finalPositions().also {
            assertEquals(2, it.size)
        }.let { (first, second) ->
            assertEquals(Position(Location(1,3), NORTH), first)
            assertEquals(Position(Location(5,1), EAST), second)
        }
    }
}