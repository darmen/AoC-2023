package y2023.d14.p2

import nthColumn
import println
import readInput
import runMeasure
import kotlin.math.abs

val directions = listOf("N", "W", "S", "E")

fun run() {
    val input = readInput()
    var inp = input
    var res = 0

    val n = 1000000000
    val cache = mutableListOf(input)

    for (k in 1..n) {
        for (j in directions.indices) {
            val d = directions[j]
            inp = tilt(d, inp)
        }


        if (cache.contains(inp)) {
            val cycleLength = abs(k - cache.indexOf(inp))
            val a1 = (n - k) / cycleLength
            val a2 = k + a1 * cycleLength
            val a3 = n - a2
            val a4 = k - cycleLength + a3
            inp = cache[a4]
            break

        } else {
            cache.add(inp)
        }
    }

    for (i in inp.indices) {
        res += inp[i].count { it == 'O' } * (inp.size - i)
    }

    res.println()
}

fun tilt(direction: String, input: List<String>): List<String> {
    val res = mutableListOf<String>()

    if (direction == "N") {
        for (i in 0..<input.first().length) {
            val s = input.nthColumn(i)

            val parts = s.split("#")

            val p = parts.joinToString("#") {
                var r = "O".repeat(it.count { c -> c == 'O' })
                r += ".".repeat(it.count { c -> c == '.' })
                r
            }

            res.add(0, p)
        }

        return rotateClockwise(res)
    }

    if (direction == "S") {
        for (i in 0..<input.first().length) {
            val s = input.nthColumn(i)

            val parts = s.split("#")

            val p = parts.joinToString("#") {
                var r = ".".repeat(it.count { c -> c == '.' })
                r += "O".repeat(it.count { c -> c == 'O' })
                r
            }

            res.add(0, p)
        }

        return rotateClockwise(res)
    }

    if (direction == "W") {
        for (i in input.indices) {
            val s = input[i]

            val parts = s.split("#")

            val p = parts.joinToString("#") {
                var r = "O".repeat(it.count { c -> c == 'O' })
                r += ".".repeat(it.count { c -> c == '.' })
                r
            }

            res += p
        }
        return res
    }

    if (direction == "E") {
        for (i in input.indices) {
            val s = input[i]

            val parts = s.split("#")

            val p = parts.joinToString("#") {
                var r = ".".repeat(it.count { c -> c == '.' })
                r += "O".repeat(it.count { c -> c == 'O' })
                r
            }

            res += p
        }
        return res
    }

    return res
}


fun rotateClockwise(matrix: List<String>): List<String> {
    val transposed = transpose(matrix)
    return transposed.map { it.reversed().joinToString("") }
}

fun transpose(matrix: List<String>): List<List<Char>> {
    val rows = matrix.size
    val cols = matrix[0].length

    return List(cols) { col ->
        List(rows) { row ->
            matrix[row][col]
        }
    }
}

fun main() {
    runMeasure { run() }
}