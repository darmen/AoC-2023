package y2024.d03

import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput().joinToString()
    var res = 0L

    val regex = Regex("""mul\(\d+,\d+\)""")
    val matches = regex.findAll(input).map { it.value }.toList()

    matches.forEach { it ->
        var cl = it.replace("mul(", "")
        cl = cl.replace(")", "")
        val parts = cl.split(",").map { it.toLong() }
        res += parts[0] * parts[1]
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}
