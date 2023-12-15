package y2023.d15.p1

import println
import readInput
import runMeasure

fun main() {
    runMeasure { solve() }
}


fun solve() {
    val input = readInput().first()

    input.split(",")
        .sumOf {
            hash(it)
        }
        .println()
}

fun hash(s: String): Int {
    return s.fold(0) { acc, char ->
        (acc + char.code) * 17 % 256
    }
}