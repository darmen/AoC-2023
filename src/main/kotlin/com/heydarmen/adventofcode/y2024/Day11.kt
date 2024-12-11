package com.heydarmen.adventofcode.y2024

class Day11(input: List<String>) {
    private var stones = input.first().split(" ").map { it.toLong() }
    private val seen = mutableMapOf<Pair<Long, Int>, Long>()

    fun solvePart1(): Long {
        return stones.sumOf { stone -> blink(stone, 25)}
    }

    fun solvePart2(): Long {
        return stones.sumOf { stone -> blink(stone, 75)}
    }

    private fun blink(stone: Long, steps: Int): StoneCount {
        if (steps == 0) {
            return 1
        }

        if (seen.containsKey(stone to steps)) {
            return seen[stone to steps]!!
        }

        val r = blinkNumber(stone, steps)
        seen[stone to steps] = r

        return r
    }

    private fun blinkNumber(value: Long, steps: Int): Long = when {
        value == 0L -> blink(1L, steps - 1)

        value.toString().length % 2 == 0 -> {
            val parts = value.toString().chunked(value.toString().length / 2).map { it.toLong() }

            blink(parts.first(), steps - 1) + blink(parts.last(), steps - 1)
        }

        else -> blink(value * 2024,steps - 1)
    }
}

private typealias StoneCount = Long
