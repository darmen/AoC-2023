package y2023.d16.p2

import println
import readInput
import runMeasure

val forks = mutableListOf<Pair<Pair<Int, Int>, Direction>>()
fun main() {
    runMeasure {
        solve()
    }
}

fun dive(i: Int, j: Int, d: Direction, input: List<CharArray>, m: List<CharArray>) {
    var cI = i
    var cJ = j
    var cDir = d

    while ((cI in input.indices) && (cJ in input[0].indices)) {
        if (forks.contains(cI to cJ to cDir)) {
            return
        }

        forks.add(cI to cJ to cDir)

        m[cI][cJ] = '#'

        val c = input[cI][cJ]

        if (c != '.') {
            val ds = reflectionMap[c to cDir]!!
            cDir = if (ds.size > 1) {
                val delta = deltas[ds.first()]!!

                val nI = cI + delta.second
                val nJ = cJ + delta.first
                val nD = ds.last()

                dive(nI, nJ, ds.first(), input, m)
                nD

            } else {
                ds.first()
            }
        }

        val delta = deltas[cDir]!!
        cI += delta.second
        cJ += delta.first
    }
}

fun solve() {
    val input = readInput().map { it.toCharArray() }

    val ms = mutableListOf<List<CharArray>>()


    for (i in input.indices) {
        var mi = input.map { it.copyOf() }
        forks.clear()
        dive(i, 0, Direction.RIGHT, input, mi)
        ms.add(mi)

        mi = input.map { it.copyOf() }
        forks.clear()
        dive(i, input[0].indices.last, Direction.LEFT, input, mi)
        ms.add(mi)
    }

    for (j in input[0].indices) {
        var mj = input.map { it.copyOf() }
        forks.clear()
        dive(0, j, Direction.DOWN, input, mj)
        ms.add(mj)

        mj = input.map { it.copyOf() }
        forks.clear()
        dive(input.indices.last, j, Direction.UP, input, mj)
        ms.add(mj)
    }

    ms.maxOf {
        it.sumOf { l -> l.count { c -> c == '#' } }
    }.println()
}

enum class Direction { UP, DOWN, LEFT, RIGHT }

val reflectionMap = mapOf(
    '|' to Direction.RIGHT to listOf(Direction.UP, Direction.DOWN),
    '|' to Direction.LEFT to listOf(Direction.UP, Direction.DOWN),
    '|' to Direction.UP to listOf(Direction.UP),
    '|' to Direction.DOWN to listOf(Direction.DOWN),

    '-' to Direction.RIGHT to listOf(Direction.RIGHT),
    '-' to Direction.LEFT to listOf(Direction.LEFT),
    '-' to Direction.UP to listOf(Direction.LEFT, Direction.RIGHT),
    '-' to Direction.DOWN to listOf(Direction.LEFT, Direction.RIGHT),

    '/' to Direction.RIGHT to listOf(Direction.UP),
    '/' to Direction.LEFT to listOf(Direction.DOWN),
    '/' to Direction.UP to listOf(Direction.RIGHT),
    '/' to Direction.DOWN to listOf(Direction.LEFT),

    '\\' to Direction.RIGHT to listOf(Direction.DOWN),
    '\\' to Direction.LEFT to listOf(Direction.UP),
    '\\' to Direction.UP to listOf(Direction.LEFT),
    '\\' to Direction.DOWN to listOf(Direction.RIGHT),
)

val deltas = mapOf(
    Direction.RIGHT to Pair(1, 0),
    Direction.LEFT to Pair(-1, 0),
    Direction.UP to Pair(0, -1),
    Direction.DOWN to Pair(0, 1),
)
