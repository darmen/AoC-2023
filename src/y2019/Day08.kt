package y2019

import println
import readInput
import runMeasure

class Day08(val input: List<String>) {
    private val width = 25
    private val height = 6

    fun solve1(): Int {
        val layer = input.first()
            .chunked(width * height)
            .minBy { s ->
                s.count { it == '0' }
            }

        return layer.count { it == '1' } * layer.count { it == '2' }
    }

    fun solve2(): Int {
        TODO("Not implemented yet")
    }
}

fun main() {
    val day = Day08(readInput())

    runMeasure {
        day.solve1().println()
    }

    runMeasure {
        day.solve2().println()
    }
}
