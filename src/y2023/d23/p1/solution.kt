package y2023.d23.p1

import Point
import println
import readInput
import runMeasure
import utils.duplicate

private fun solve() {
    val input = readInput().map { it.toCharArray() }

    var steps = 1
    val target = input.size - 1 to input.last().size - 2

    val streams = mutableListOf(Stream(mutableListOf(0 to 1), 0))

    while (streams.any { !it.hasReachedTarget(target) }) {
        val iterator = streams.listIterator()
        for (stream in iterator) {
            val point = stream.points.last()

            if (point == target) {
                continue
            }

            val nms = findNextMoves(point, stream.points, input)

            val newStream = Stream(stream.points.toMutableList(), stream.steps)
            for (np in nms) {
                if (nms.indexOf(np) == 0) {
                    stream.points.add(np)
                    stream.steps++
                } else {
                    newStream.points.add(np)
                    newStream.steps++
                    iterator.add(newStream)
                    iterator.previous()
                }
            }
        }
    }

    streams
        .maxBy { it.steps }
        .steps
        .println()
}

private fun findNextMoves(b: Point, v: List<Point>, map: List<CharArray>): List<Point> {
    return Direction.entries.filter { d ->
        val np = b.first + d.d.first to b.second + d.d.second
        val pointInBounds = np.first >= 0 && np.first < map.size && np.second >= 0 && np.second < map.last().size - 1
        val pointIsGoodSlope = { p: Point ->
            if (!slopes.keys.contains(map[np.first][np.second])) {
                false
            } else {
                val char = map[p.first][p.second]
                val nd = slopes[char]!!

                nd != d.reversed()
            }
        }

        pointInBounds
                && !v.contains(np)
                && map[np.first][np.second] != '#'
                && (map[np.first][np.second] == '.' || pointIsGoodSlope(np))
    }
        .map {
            val np = b.first + it.d.first to b.second + it.d.second
            np
        }
}

fun draw(map: List<CharArray>, points: List<Point>) {
    val temp = map.duplicate()

    for (p in points) {
        temp[p.first][p.second] = 'O';
    }

    temp
        .map { it.joinToString("") }
        .joinToString("\n")
        .println()

    println("")
}

fun main() {
    runMeasure { solve() }
}

private val slopes = mapOf(
    '>' to Direction.RIGHT,
    '<' to Direction.LEFT,
    'v' to Direction.DOWN,
    '^' to Direction.UP,
)

private data class Stream(
    val points: MutableList<Point>,
    var steps: Int = 0
) {
    fun hasReachedTarget(target: Point): Boolean = points.isNotEmpty() && points.last() == target
}

private enum class Direction(val d: Pair<Int, Int>) {
    UP(-1 to 0), DOWN(1 to 0), LEFT(0 to -1), RIGHT(0 to 1);

    fun reversed(): Direction =
        when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
}