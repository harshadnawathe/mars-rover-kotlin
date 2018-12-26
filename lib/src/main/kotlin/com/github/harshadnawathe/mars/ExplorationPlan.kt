package com.github.harshadnawathe.mars

class ExplorationPlan(val plateau: Plateau, val deployments: List<Deployment>) {

    fun finalPositions() : List<Position> {
        return deployments.map { deployment ->
            val rover = Rover(deployment.initialPosition, plateau::isWithinBounds)
            deployment.commands.forEach { rover.apply(it) }
            rover.position
        }
    }
}

private fun Rover.apply(command: Command): Any = when (command) {
    Command.LEFT -> turnLeft()
    Command.RIGHT -> turnRight()
    Command.MOVE -> move()
}

