package com.github.harshadnawathe.mars

import com.github.harshadnawathe.mars.TokenType.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import kotlin.IllegalArgumentException

internal enum class TokenType {
    EOF,
    PLATEAU,
    ROVER_POSITION,
    COMMANDS,
    EMPTY_LINE,
    ERROR
}

internal data class Token(val type: TokenType, val lineNumber: Int, val data: String) {
    constructor() : this(EOF, -1, "")
}


internal class Scanner(private val input: String) : Iterator<Token> {
    private var eof = input.isEmpty()
    private var position = 0
    private var lineNumber = 0

    override fun hasNext() = !eof

    override fun next(): Token {
        if (eof) return Token()

        val endOfLine = input.findAnyOf(LINE_ENDINGS, position)
        val next = endOfLine?.first ?: input.length
        val data = input.substring(position, next).trim()
        val token = when {
            data.isBlank() -> Token(EMPTY_LINE, lineNumber, data)
            data.matches(ROVER_POSITION_PATTERN) -> Token(ROVER_POSITION, lineNumber, data)
            data.matches(COMMANDS_PATTERN) -> Token(COMMANDS, lineNumber, data)
            data.matches(PLATEAU_PATTERN) -> Token(PLATEAU, lineNumber, data)
            else -> Token(ERROR, lineNumber, data)
        }

        if (next == input.length) {
            eof = true
        } else {
            position = next + 1
            lineNumber++
        }

        return token
    }

    companion object {
        val LINE_ENDINGS = listOf("\n", "\r\n", "\r")
        val ROVER_POSITION_PATTERN = Regex("^\\d+\\s+\\d+\\s+[NESW]\$")
        val COMMANDS_PATTERN = Regex("^[LMR]+\$")
        val PLATEAU_PATTERN = Regex("^\\d+\\s+\\d+\$")
    }
}

class ParserError(lineNumber: Int, lineText: String, msg: String?) :
        RuntimeException("Parser error on line $lineNumber for text $lineText${description(msg)}") {
    constructor(lineNumber: Int, lineText: String) : this(lineNumber, lineText, null)
}

private fun description(msg: String?) = msg?.let { "\n$it\n" } ?: ""

class Parser(text: String) {
    constructor(stream: InputStream) : this(stream.bufferedReader().use(BufferedReader::readText))

    private var tokens: List<Token> = Scanner(text).asSequence()
            .filter { it.type != EMPTY_LINE }
            .toList()
            .also { list ->
                list.find { it.type == ERROR }?.let {
                    throw ParserError(it.lineNumber, it.data, "Unrecognised token")
                }
            }


    //explorationPlan
    //plateau deployments
    fun explorationPlan() = ExplorationPlan(plateau(), deployments())

    //plateau
    private fun plateau(): Plateau {
        return pop()
                .also { token ->
                    if (token.type != PLATEAU) {
                        throw ParserError(
                                token.lineNumber,
                                token.data,
                                "Expected token of type PLATEAU found ${token.type}"
                        )
                    }
                }
                .data
                .split(Regex("\\s+"))
                .map(String::toInt)
                .let { (x, y) ->
                    Plateau(Location(x, y))
                }
    }

    //deployments
    //deployment ...
    private fun deployments(): List<Deployment> {
        val deployments: MutableList<Deployment> = mutableListOf()

        do {
            deployments.add(deployment())
        } while (peek().type != EOF)

        return deployments
    }

    //deployment
    //roverPosition commands
    private fun deployment() = Deployment(roverPosition(), commands())

    //roverPosition
    private fun roverPosition(): Position {
        return pop()
                .also { token ->
                    if (token.type != ROVER_POSITION) {
                        throw ParserError(token.lineNumber, token.data, "Expected ROVER_POSITION found ${token.type}")
                    }
                }
                .data
                .split(Regex("\\s+"))
                .let{(xText,yText,directionText) ->
                    Position(Location(xText.toInt(), yText.toInt()), directionFrom(directionText))
                }
    }

    //commands
    private fun commands(): List<Command> {
        return pop()
                .also { token ->
                    if (token.type != COMMANDS) {
                        throw ParserError(
                                token.lineNumber,
                                token.data,
                                "Expected token of type COMMANDS found ${token.type}"
                        )
                    }
                }
                .data
                .map { commandFrom(it)}
    }


    private fun peek(): Token {
        if (tokens.isEmpty()) {
            return Token()
        }

        return tokens.first()
    }

    private fun pop(): Token {
        if (tokens.isEmpty()) {
            return Token()
        }

        val token = tokens.first()
        tokens = tokens.drop(1)

        return token
    }

    private fun directionFrom(str : String) = when(str.toUpperCase()) {
        "N" -> Direction.NORTH
        "E" -> Direction.EAST
        "S" -> Direction.SOUTH
        "W" -> Direction.WEST
        else -> throw IllegalArgumentException("Invalid Direction string $str")
    }

    private fun commandFrom(commandLiteral : Char) = when(commandLiteral) {
        'L' -> Command.LEFT
        'R' -> Command.RIGHT
        'M' -> Command.MOVE
        else -> throw IllegalArgumentException("Invalid Command literal $commandLiteral")
    }
}