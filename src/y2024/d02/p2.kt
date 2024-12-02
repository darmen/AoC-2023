package y2024.d02

import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput()

    var res = 0L

    input.forEach { s ->
        val list = s.split(' ').map(String::toInt)
        val diffs = getDiffs(list)

        var safe = isSafe(diffs)

        if (!safe) {
            for (i in list.indices) {
                val newList = list.filterIndexed { index, _ -> index != i }
                safe = isSafe(getDiffs(newList))
                if (safe) break
            }
        }

        val p = if (safe) 1 else 0
        res += p
    }

    res.println()
}

fun getDiffs(list: List<Int>): List<Int> = list.zipWithNext { a, b -> b - a }

fun isSafe(diffs: List<Int>): Boolean {
    val condition = diffs.all { it in 1..3 } || diffs.all { it in -3..-1 }
    return condition
}

fun main() {
    runMeasure { solve() }
}
