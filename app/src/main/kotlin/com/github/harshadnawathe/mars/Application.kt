import com.github.harshadnawathe.mars.Parser
import com.github.harshadnawathe.mars.Position
import com.github.harshadnawathe.mars.print

fun main(args: Array<String>) {

    Parser(System.`in`)
            .explorationPlan()
            .finalPositions()
            .forEach(Position::print)
}
