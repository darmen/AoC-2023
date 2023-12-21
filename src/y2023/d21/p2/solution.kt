package y2023.d21.p2

import println
import readInput
import runMeasure
import utils.duplicate

// Sketch – https://virtual-graph-paper.com/NzYxZWVhMTAwYzg4
fun draw(start: Pair<Int, Int>, input: List<CharArray>, maxSteps: Int = 65): Int {
    val centers = mutableListOf(start)

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

        inputResult.map { it.joinToString("") }.joinToString("\n").println()
        println("")

        return inputResult to newCenters
    }

    var rr = input to centers.toList()


    for (j in 1..maxSteps) {
        rr = rec(rr.first, rr.second)
    }


//    println(rr.second.size)
//    println("")

    return rr.second.size
}

fun solve() {
    val input = readInput().map { it.toCharArray() }
    val r = (input.size - 1)
    val c = input[0].size - 1

    val startLocations = mapOf(
//        "c" to (r / 2 to c / 2),
//        "b" to (r to c / 2),
//        "r" to (r / 2 to c),
//        "t" to (0 to c / 2),
//        "l" to (r / 2 to 0),
        "tr" to (0 to c),
        "tl" to (0 to 0),
        "br" to (r to c),
        "bl" to (r to 0),
    )

    val gardenPlots = mutableMapOf<String, Int>()

    for (l in startLocations) {
        gardenPlots[l.key] = draw(l.value, input)
    }

    val k = 26501365 / 65

    val nSquares = k + 2 * (1..k - 2 step 2).sum()

    val maxSteps = 26501365 % 65

    for (l in startLocations) {
        gardenPlots[l.key] = gardenPlots[l.key]!! + draw(l.value, input, maxSteps)
    }

    val totalGardenPlots = (nSquares * gardenPlots["c"]!! +
            4 * gardenPlots["tr"]!! +
            4 * gardenPlots["tl"]!! +
            4 * gardenPlots["bl"]!! +
            4 * gardenPlots["br"]!!)


    totalGardenPlots.println()
}

fun main() {
    runMeasure { solve() }
}

typealias R = Int
typealias C = Int

private enum class Direction(val delta: Pair<R, C>) {
    UP(-1 to 0), DOWN(1 to 0), LEFT(0 to -1), RIGHT(0 to 1), ;
}