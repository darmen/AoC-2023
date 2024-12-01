package y2024.d01.p2

import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput().map {
        it.split("   ").map { num -> num.toInt() }.let { p -> p[0] to p[1] }
    }

    val first = input.map { it.first }.sorted()
    val second = input.map { it.second }.sorted()

    var res = 0L

    first.forEachIndexed { index, f ->
        var appearance = 0
        second.forEach { s -> if (s == f) appearance += 1 }
        res += f * appearance
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}
