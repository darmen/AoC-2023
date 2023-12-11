package y2023.d11.p2

import readInput
import kotlin.math.abs

fun main() {
    val input = readInput()

    val emptyCols = mutableListOf<Int>()
    val emptyRows = mutableListOf<Int>()

    for (i in input.first().indices) {
        val col = input.map { it[i] }.joinToString("")

        if (col.all { it == '.' }) {
            emptyCols.add(i)
        }

        if (input[i].all { it == '.' }) {
            emptyRows.add(i)
        }
    }


    val coords = mutableListOf<Pair<Int, Int>>()
    for (i in input.indices) {
        val s = input[i]
        for (j in s.indices) {
            if (s[j] == '#') {
                coords.add(Pair(i, j))
            }
        }
    }

    var sum = 0L
    val n: Long = 1_000_000 - 1
    for (i in coords.indices) {
        for (j in i + 1..<coords.size) {
            var rowsCount = emptyRows.count { it < coords[i].first }
            var colsCount = emptyCols.count { it < coords[i].second }
            val d1 = Pair(
                coords[i].first + (rowsCount * n), coords[i].second + (colsCount * n)
            )

            rowsCount = emptyRows.count { it < coords[j].first }
            colsCount = emptyCols.count { it < coords[j].second }
            val d2 = Pair(
                coords[j].first + (rowsCount * n), coords[j].second + (colsCount * n)
            )

            if (d1.first == d2.first) {
                sum += abs(d2.second - d1.second)
                continue
            }

            if (d1.second == d2.second) {
                sum += abs(d2.first - d1.first)
                continue
            }

            sum += abs(d1.second - d2.second) + abs(d1.first - d2.first)
        }
    }

    println(sum)
}
