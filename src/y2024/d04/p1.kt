package y2024.d04

import countOccurrences
import extractDiagonals
import println
import readInput
import rotateLeft
import runMeasure

private fun solve() {
    val input = readInput()

    var res = 0L

    val lines = mutableListOf<String>()

    // horizontal 1
    lines.addAll(input)

    // vertical 1
    lines.addAll(rotateLeft(input))

    // diagonal 1
    lines.addAll(extractDiagonals(input))

    lines.forEach {
        res += countOccurrences(it, "XMAS")
        res += countOccurrences(it, "SAMX")
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}

