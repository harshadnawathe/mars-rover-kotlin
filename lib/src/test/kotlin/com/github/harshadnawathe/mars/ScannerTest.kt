package com.github.harshadnawathe.mars

import org.junit.Assert.*
import org.junit.Test

class ScannerTest{
    @Test
    fun shouldScanPlateauToken() {
        val text = """
            5 5
        """.trimIndent()

        val scanner = Scanner(text)
        assertTrue(scanner.hasNext())

        val token = scanner.next()
        assertEquals(TokenType.PLATEAU, token.type)
        assertEquals("5 5", token.data)
    }

    @Test
    fun shouldScanRoverPositionToken() {
        val text = """
        1 2 N

        LMLMLMLMM
        """.trimIndent()

        val scanner = Scanner(text)
        assertTrue(scanner.hasNext())

        val token = scanner.next()
        assertEquals(TokenType.ROVER_POSITION, token.type)
        assertEquals("1 2 N", token.data)
    }

    @Test
    fun shouldScanCommandsToken() {
        val text = """
        LMLMLMLMM
        """.trimIndent()

        val scanner = Scanner(text)
        assertTrue(scanner.hasNext())

        val token = scanner.next()
        assertEquals(TokenType.COMMANDS, token.type)
        assertEquals("LMLMLMLMM", token.data)
    }

    @Test
    fun shouldScanEmptyLineToken() {
        val text = """

        LMLMLMLMM
        """.trimIndent()

        val scanner = Scanner(text)
        assertTrue(scanner.hasNext())

        val token = scanner.next()
        assertEquals(TokenType.EMPTY_LINE, token.type)
        assertEquals("", token.data)
    }

    @Test
    fun shouldScanUnknownToken() {
        val text = """
        fooBar_LMLMLMLMM
        """.trimIndent()

        val scanner = Scanner(text)
        assertTrue(scanner.hasNext())

        val token = scanner.next()
        assertEquals(TokenType.ERROR, token.type)
        assertEquals("fooBar_LMLMLMLMM", token.data)
    }

    @Test
    fun shouldReportLineNumbersInToken() {
        val text = """
            5 5

            1 2 N

            LMLMLMLMM

            3 3 E

            MMRMMRMRRM
            """.trimIndent()

        val roverPositions = Scanner(text).asSequence()
                .filter { it.type == TokenType.ROVER_POSITION }
                .toList()
        assertTrue(roverPositions.isNotEmpty())
        assertEquals(2, roverPositions[0].lineNumber)
        assertEquals(6, roverPositions[1].lineNumber)
    }
}