package com.heydarmen.adventofcode.y2024

import com.heydarmen.adventofcode.Direction
import com.heydarmen.adventofcode.Point2D

class Day06(input: List<String>) {
    private val grid = input.flatMapIndexed { y, row ->
        row.mapIndexed { x, char -> Point2D(x, y) to char }
    }.toMap()

    private val start = grid.entries.first { it.value == '^' }

    fun solvePart1(): Long {
        return traverse().first.size.toLong()
    }

    fun solvePart2(): Long {
        return traverse()
            .first.filter { it != start.key }
            .count { stone -> traverse(stone).second }
            .toLong()
    }

    private fun traverse(obstacle: Point2D? = null): Pair<Set<Point2D>, Boolean> {

        var direction = Direction.UP

        val seen = mutableSetOf<Pair<Point2D, Direction>>()
        var currentPoint = start.key

        while (currentPoint in grid && (currentPoint to direction) !in seen) {
            seen.add(currentPoint to direction)

            val nextPoint = currentPoint.tap(direction)

            if (grid[nextPoint] == '#' || nextPoint == obstacle) {
                direction = direction.turnRight()
            } else {
                currentPoint = currentPoint.tap(direction)
            }

        }

        return seen.map { it.first }.toSet() to ((currentPoint to direction) in seen)
    }
}
