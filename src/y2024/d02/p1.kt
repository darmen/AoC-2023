package y2024.d02

import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput()

    var res = 0L

    input.forEach { s ->
        val list = s.split(' ').map(String::toInt)
        val diffs = list.zipWithNext { a, b -> a - b }

        val p = if (diffs.all{it in 1..3} || diffs.all { it in -3..-1 }) 1 else 0
        res += p
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}
