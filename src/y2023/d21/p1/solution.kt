package y2023.d21.p1

import readInput
import runMeasure
import utils.duplicate

fun solve() {
    val input = readInput().map { it.toCharArray() }

    val centers = mutableListOf<Pair<Int, Int>>()

    for (i in input.indices) {
        val s = input[i]

        if (s.contains('S')) {
            centers.add(i to s.indexOf('S'))
            break
        }
    }


    fun rec(input: List<CharArray>, centers: List<Pair<Int, Int>>): Pair<List<CharArray>, List<Pair<Int, Int>>> {
        val newCenters = mutableListOf<Pair<Int, Int>>()
        val inputResult = input.duplicate()

        for (c in centers) {
            inputResult[c.first][c.second] = '.'

            for (d in Direction.entries) {
                val (nr, nc) = c.first + d.delta.first to c.second + d.delta.second

                if ((nr < 0 || nr > input.size - 1) || (nc < 0 || nc > input[0].size - 1)) continue
                if (inputResult[nr][nc] == '#') continue

                inputResult[nr][nc] = 'O'

                if (!newCenters.contains(nr to nc)) {
                    newCenters.add(nr to nc)
                }
            }
        }

//        inputResult.map { it.joinToString(" ") }.joinToString("\n").println()
//        println("")

        return inputResult to newCenters
    }

    var rr = input to centers.toList()

    for (j in 1..64) {
        rr = rec(rr.first, rr.second)
    }

    println(rr.first.fold(0) { acc, chars -> acc + chars.count { it == 'O' } })
}

fun main() {
    runMeasure { solve() }
}

typealias R = Int
typealias C = Int

private enum class Direction(val delta: Pair<R, C>) {
    UP(-1 to 0), DOWN(1 to 0), LEFT(0 to -1), RIGHT(0 to 1), ;
}