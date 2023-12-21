package y2023.d21.p2

import println
import readInput
import runMeasure
import utils.duplicate
import kotlin.math.ceil

// Sketch – https://virtual-graph-paper.com/NzYxZWVhMTAwYzg4
fun draw(input: List<CharArray>, maxSteps: Int = 65): Int {
    val centers = mutableListOf((input.size - 1) / 2 to (input[0].size - 1) / 2)

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

//        inputResult.map { it.joinToString("") }.joinToString("\n").println()
//        println("")

        return inputResult to newCenters
    }

    var rr = input to centers.toList()


    for (j in 1..maxSteps) {
        rr = rec(rr.first, rr.second)
    }


//    println(rr.second.size)
    println("")
    return rr.first.fold(0) { acc, chars -> acc + chars.count { it == 'O' } }

//    return rr.second.size
}

/**
 * @see "y20023/d21/graph.png" for my visualized thoughts
 */
fun solve() {
    val input = readInput().map { it.toCharArray() }

    val countInSquare = draw(input, 130)
    val countInDiamondPile = draw(input, 64)
    val countInCornersTotal = countInSquare - countInDiamondPile
    val countPerCorner = countInCornersTotal / 4

    val desiredSteps = 26501365L
    val totalSquares = desiredSteps / 131
    val evenSquares = totalSquares * totalSquares
    val oddSquares = (totalSquares + 1) * (totalSquares + 1)

    val evenCorners = 2 * totalSquares
    val oddCorners = 2 * (totalSquares + 1)

    println(countInSquare * evenSquares + countInSquare * oddSquares + evenCorners * countPerCorner - oddCorners * countPerCorner + countInDiamondPile)
}

fun main() {
    runMeasure { solve() }
}

typealias R = Int
typealias C = Int

private enum class Direction(val delta: Pair<R, C>) {
    UP(-1 to 0), DOWN(1 to 0), LEFT(0 to -1), RIGHT(0 to 1), ;
}
