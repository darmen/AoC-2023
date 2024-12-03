package y2024.d03

import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput().joinToString()
    var res = 0L

    val regex = Regex("""(mul\(\d+,\d+\)|do\(\))|don't\(\)""")
    val matches = regex.findAll(input).map { it.value }.toList()

    var enabled = true

    matches.forEach { it ->
        if (it.contains("mul")) {
            var cl = it.replace("mul(", "")
            cl = cl.replace(")", "")
            val parts = cl.split(",").map { it.toLong() }
            if (enabled) res += parts[0] * parts[1]
        } else {
            enabled = it.contains("do()")
        }
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}
