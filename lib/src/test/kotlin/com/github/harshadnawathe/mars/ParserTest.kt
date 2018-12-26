package com.github.harshadnawathe.mars

import com.github.harshadnawathe.mars.Command.LEFT
import com.github.harshadnawathe.mars.Command.MOVE
import com.github.harshadnawathe.mars.Direction.EAST
import com.github.harshadnawathe.mars.Direction.NORTH
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

class ParserTest {

    @Test
    fun shouldThrowErrorOnInvalidTextInput() {
        val text = """
            5 5

            1 2 N

            LMLMLMLMM

            3 3 E

            THERE IS AN ERROR IN THIS LINE

            MMRMMRMRRM
            """.trimIndent()

        try {
            Parser(text)
            fail()
        } catch (error: ParserError) {
            error.message.let { message ->
                assertNotNull(message)
                assertEquals("Parser error on line 8 for text THERE IS AN ERROR IN THIS LINE\nUnrecognised token\n", message)
            }
        }
    }

    @Test
    fun shouldThrowErrorOnInvalidSyntax() {
        val text = """
            5 5

            1 2 N

            3 3 E

            MMRMMRMRRM
            """.trimIndent()

        try {
            Parser(text).explorationPlan()
            fail()
        } catch (error: ParserError) {
            error.message.let { message ->
                assertNotNull(message)
                assertEquals("Parser error on line 4 for text 3 3 E\n" +
                        "Expected token of type COMMANDS found ROVER_POSITION\n", message)
            }
        }

    }

    @Test
    fun shouldParseExplorationPlan() {
        val text = """
            5 5

            1 2 N

            LM

            3 3 E

            MM
            """.trimIndent()

        val explorationPlan = Parser(text).explorationPlan()

        assertThat(explorationPlan.plateau.topRight, `is`(Location(5, 5)))
        assertEquals(2, explorationPlan.deployments.size)

        val firstDeployment = explorationPlan.deployments[0]
        assertEquals(Position(Location(1, 2), NORTH), firstDeployment.initialPosition)
        assertEquals(arrayListOf(LEFT, MOVE), firstDeployment.commands)

        val secondDeployment = explorationPlan.deployments[1]
        assertEquals(Position(Location(3, 3), EAST), secondDeployment.initialPosition)
        assertEquals(arrayListOf(MOVE, MOVE), secondDeployment.commands)

    }
}