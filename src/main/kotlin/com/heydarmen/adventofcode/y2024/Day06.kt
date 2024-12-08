package com.heydarmen.adventofcode.y2024

import com.heydarmen.adventofcode.Direction
import com.heydarmen.adventofcode.Point2D

class Day06(input: List<String>) {
    private val grid = input.flatMapIndexed { y, row ->
        row.mapIndexed { x, char -> Point2D(x, y) to char }
    }.toMap()

    fun solvePart1(): Long {
        return traverse()
    }

    private fun traverse(): Long {
        val start = grid.entries.first { it.value == '^' }

        var direction = Direction.UP

        val seen = mutableSetOf<Pair<Point2D, Direction>>()
        var currentPoint = start.key

        while (currentPoint in grid.keys && (currentPoint to direction) !in seen) {
            seen.add(currentPoint to direction)

            val nextPoint = currentPoint.tap(direction)

            if (grid[nextPoint] == '#') {
                direction = direction.turnRight()
            }

            currentPoint = currentPoint.tap(direction)
        }

        return seen.distinctBy { it.first }.size.toLong()
    }
}
