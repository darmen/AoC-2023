package y2019

import Direction
import Point2D
import println
import readInput

class Day03(val input: List<String>) {
    private val wire1 = buildGrid(input.first())
    private val wire2 = buildGrid(input.last())

    private fun buildGrid(wire: String): Set<Point2D> = buildSet {
        var point = Point2D(0, 0)
        this.add(point)

        wire.split(",").forEach {
            val steps = it.drop(1).toInt()

            val direction = when (it.first()) {
                'R' -> Direction.RIGHT
                'L' -> Direction.LEFT
                'U' -> Direction.UP
                'D' -> Direction.DOWN
                else -> throw IllegalArgumentException("Unrecognized direction")
            }

            for (i in 1..steps) {
                point = Point2D(point.x + direction.d.first, point.y + direction.d.second)
                this.add(point)
            }
        }
    }

    fun solve1(): Int {
        return wire1
            .intersect(wire2)
            .filter { it != Point2D(0, 0) }
            .minOfOrNull {
                it.manhattanDistance(Point2D(0, 0))
            }!!
    }

    fun solve2(): Any {
        TODO("Not implemented yet")
    }
}

fun main() {
    val day = Day03(readInput())

    day.solve1().println()
    day.solve2().println()
}