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

    fun solve2(): String {
        val layers = input.first().chunked(width * height)
        val topLayer = mutableListOf<Char>()

        for (i in 0..<layers.first().length) {
            for (j in layers.indices) {
                if (layers[j][i] == '2') {
                    continue
                } else {
                    topLayer.add(layers[j][i])
                    break
                }
            }
        }

        return topLayer.joinToString("")
            .chunked(width)
            .joinToString("\n")
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
