package com.heydarmen.adventofcode.y2024

import com.heydarmen.adventofcode.Direction
import com.heydarmen.adventofcode.Point2D

class Day10(input: List<String>) {
    private val grid = input.flatMapIndexed { y: Int, s: String ->
        s.mapIndexed { x, c ->
            Point2D(x, y) to c.digitToInt()
        }
    }.toMap()

    private val trailheads = grid.entries.filter { it.value == 0 }.map { it.key }

    fun solvePart2(): Long {
        val scores = mutableMapOf<Point2D, Long>()

        val result = trailheads.sumOf { t ->
            findNumberOfDistinctTrails(grid, t to 0, scores)
        }

        return result
    }

    fun solvePart1(): Long {
        val reachables = mutableMapOf<Point2D, Set<Point2D>>()

        val score = trailheads.sumOf { t ->
            findReachableSummits(grid, t to 0, reachables).size
        }.toLong()

        return score
    }

    private fun findNumberOfDistinctTrails(grid: Map<Point2D, Int>, current: Pair<Point2D, Int>, scores: MutableMap<Point2D, Long>): Long {
        if (current.second == 9) {
            return 1
        }

        if (!grid.containsKey(current.first)) {
            return 0
        }

        if (scores.contains(current.first) && scores[current.first]!! > 0) {
            return scores[current.first]!!
        }

        var score = 0L

        for (d in Direction.entries) {
            val next = current.first.tap(d)

            if (!this.grid.containsKey(next)) {
                continue
            }

            val nextHeight = this.grid[next]!!

            if (nextHeight - current.second == 1) {
                score += findNumberOfDistinctTrails(grid, next to nextHeight, scores)
                scores[current.first] = score
            }
        }

        return score
    }

    private fun findReachableSummits(grid: Map<Point2D, Int>, current: Pair<Point2D, Int>, reachables: MutableMap<Point2D, Set<Point2D>>): Set<Point2D> {
        if (current.second == 9) {
            return setOf(current.first)
        }

        if (!grid.containsKey(current.first)) {
            return emptySet()
        }

        if (reachables.contains(current.first) && reachables[current.first]!!.isNotEmpty()) {
            return reachables[current.first]!!
        }

        val summitsReachable = mutableSetOf<Point2D>()

        for (d in Direction.entries) {
            val next = current.first.tap(d)

            if (!this.grid.containsKey(next)) {
                continue
            }

            val nextHeight = this.grid[next]!!

            if (nextHeight - current.second == 1) {
                summitsReachable += findReachableSummits(grid, next to nextHeight, reachables)
                reachables[current.first] = summitsReachable
            }
        }

        return summitsReachable
    }
}
