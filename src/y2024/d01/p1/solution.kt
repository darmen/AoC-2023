package y2024.d01.p1

import println
import readInput
import runMeasure
import kotlin.math.abs

private fun solve() {
    val input = readInput().map {
        it.split("   ").map { num -> num.toInt() }.let { p -> p[0] to p[1] }
    }

    val first = input.map { it.first }.sorted()
    val second = input.map { it.second }.sorted()

    var res = 0L

    first.forEachIndexed { index, f ->
        val s = second[index]
        res += abs(s - f)
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}
