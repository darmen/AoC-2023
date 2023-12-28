package y2023.d23.p2

import Point2D
import println
import readInput
import runMeasure
import kotlin.math.max

/**
 * Greatly inspired by https://todd.ginsberg.com/post/advent-of-code/2023/day23/
 */
class Day23Part2(input: List<String>) {
    private val grid = input.map { it.toCharArray() }.toTypedArray()
    private val start = Point2D(input.first().indexOfFirst { it == '.' }, 0)
    private val target = Point2D(input.last().indexOfLast { it == '.' }, input.lastIndex)

    operator fun invoke(): Int {
        val reducedGrid = reduceGrid()
        return traverse { location ->
            reducedGrid
                .getValue(location)
                .map { it.key to it.value }
        }
    }

    private fun Array<CharArray>.findDecisionPoints() = buildSet {
        add(start)
        add(target)
        this@findDecisionPoints.forEachIndexed { y, chars ->
            chars.forEachIndexed { x, c ->
                if (c != '#') {
                    Point2D(x, y).apply {
                        val hasValidNeighbors = cardinalNeighbors()
                            .filter { grid.isSafe(it) }
                            .filter { grid[it.y][it.x] != '#' }
                            .size > 2

                        if (hasValidNeighbors) {
                            add(this)
                        }
                    }
                }
            }
        }
    }

    private fun traverse(nextLocations: (Point2D) -> List<Pair<Point2D, Int>>): Int {
        var best = 0
        val visited = mutableSetOf<Point2D>()

        fun traverseWork(location: Point2D, steps: Int): Int {
            if (location == target) {
                best = max(steps, best)
                return best
            }
            visited += location
            nextLocations(location)
                .filter { (place, _) -> place !in visited }
                .forEach { (place, distance) -> traverseWork(place, distance + steps) }
            visited -= location
            return best
        }

        return traverseWork(start, 0)
    }

    private fun reduceGrid(): Map<Point2D, Map<Point2D, Int>> {
        return grid.findDecisionPoints().let {
            it.associateWith { p -> reduceGridFromPoint(p, it) }
        }
    }

    private fun reduceGridFromPoint(from: Point2D, toAnyOther: Set<Point2D>): Map<Point2D, Int> {
        val queue = ArrayDeque<Pair<Point2D, Int>>().apply {
            add(from to 0)
        }

        val seen = mutableSetOf(from)

        val answer = mutableMapOf<Point2D, Int>()

        while (queue.isNotEmpty()) {
            val (location, distance) = queue.removeFirst()

            if (location != from && location in toAnyOther) {
                answer[location] = distance
            } else {
                location.cardinalNeighbors()
                    .filter { grid.isSafe(it) }
                    .filter { grid[it.y][it.x] != '#' }
                    .filter { it !in seen }
                    .forEach {
                        seen += it
                        queue.add(it to distance + 1)
                    }
            }
        }
        return answer
    }


    private fun Array<CharArray>.isSafe(point: Point2D): Boolean =
        point.x in indices && point.y in 0..<this.first().size
}


fun main() {
    runMeasure {
        Day23Part2(readInput())()
            .println()
    }
}