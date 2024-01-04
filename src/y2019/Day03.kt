package y2019

import Direction
import Point2D
import println
import readInput
import runMeasure

class Day03(val input: List<String>) {


    private fun buildGridPart1(wire: String): Set<Point2D> = buildSet {
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

    private fun buildGridPart2(wire: String): Map<Int, Point2D> = buildMap {
        var point = Point2D(0, 0)
        this[0] = point

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

                if (!this.containsValue(point)) {
                    this[this.keys.size] = point
                }
            }
        }
    }

    fun solve1(): Int {
        val wire1 = buildGridPart1(input.first())
        val wire2 = buildGridPart1(input.last())

        return wire1
            .intersect(wire2)
            .filter { it != Point2D(0, 0) }
            .minOfOrNull {
                it.manhattanDistance(Point2D(0, 0))
            }!!
    }

    fun solve2(): Any {
        val wire1 = buildGridPart2(input.first())
        val wire2 = buildGridPart2(input.last())

        val intersections = wire1.values
            .intersect(wire2.values.toSet())
            .filterNot { it == Point2D(0, 0) }

        return intersections.minOfOrNull {
            wire1.filterValues { v -> v == it }.keys.first() + wire2.filterValues { v -> v == it }.keys.first()
        }!!
    }
}

fun main() {
    runMeasure {
        val day = Day03(readInput())
        day.solve1().println()
        day.solve2().println()
    }
}