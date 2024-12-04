package y2024.d04

import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput()

    var res = 0L

    val l = listOf("MS", "SM")
    input.forEachIndexed { i, row ->
        row.forEachIndexed { j, c ->
            if (i > 0 && j > 0 && i < input.size - 1 && j < row.length - 1) {
                if (c == 'A') {
                    val topLeft = input[i - 1][j - 1]
                    val topRight = input[i - 1][j + 1]
                    val bottomLeft = input[i + 1][j - 1]
                    val bottomRight = input[i + 1][j + 1]

                    if (l.contains("$topLeft$bottomRight") && l.contains("$bottomLeft$topRight")) {
                        res += 1
                    }
                }
            }
        }
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}
