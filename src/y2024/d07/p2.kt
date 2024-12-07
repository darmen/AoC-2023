package y2024.d07

import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput()

    var res = 0L

    outer@for (it in input) {
        val parts = it.split(": ")
        val result = parts.first().toLong()
        val operands = parts[1].split(" ").map { it.toLong() }

        val permutations = sizedPermutations(operands.size - 1)

        for (i in permutations.indices) {
            if (calc(permutations[i], operands) == result) {
                res += result
                continue@outer
            }
        }
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}

private fun calc(operators: List<String>, operands: List<Long>): Long {
    var res = operands.first()
    val numbers = operands.drop(1)

    numbers.forEachIndexed { index, _->
        val c = operators[index]

        if (c == "+") {
            res += numbers[index]
        }

        if (c == "*") {
            res *= numbers[index]
        }

        if (c == "||") {
            res = "$res${numbers[index]}".toLong()
        }
    }

    return res
}

private fun sizedPermutations(size: Int): List<List<String>> {
    val elements = listOf("+", "*", "||")

    if (size == 0) return listOf(emptyList())

    val smallerPermutations = sizedPermutations(size - 1)

    return smallerPermutations.flatMap {
        elements.map { element -> it + element }
    }
}
