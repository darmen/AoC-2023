package y2023.d18.p1

import println
import readInput
import runMeasure
import kotlin.math.abs

fun main() {
    runMeasure {
        solve()
    }
}


fun solve() {
    val input = readInput()

    var point = 0L to 0L

    var area = 0L

    var bounds = 0
    for (i in input.indices) {
        val s = input[i]
        val dir = Direction.from(s.split(" ").first())
        val n = s.split(" ")[1].toInt()
        bounds += n

        for (k in 1..n) {
            val nextPoint = point.first + 1 * dir.delta.second to point.second + 1 * dir.delta.first
            val (x1, y1) = point
            val (x2, y2) = nextPoint
            area += x1 * y2 - x2 * y1

            point = nextPoint
        }
    }

    val (x1, y1) = point
    val (x2, y2) = 0 to 0
    area += (x1 * y2 - x2 * y1)

    area = abs(area) / 2

    (bounds + area + 1 - bounds / 2).println()
}

private enum class Direction(val delta: Pair<Int, Int>) {
    UP(0 to -1),
    DOWN(0 to 1),
    LEFT(-1 to 0),
    RIGHT(1 to 0);

    companion object {
        fun from(s: String): Direction {
            return when (s) {
                "U" -> UP
                "D" -> DOWN
                "L" -> LEFT
                else -> RIGHT
            }
        }
    }
}