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

fun hash(s: String): Long {
    var res = 0L
    s.forEach {
        res += it.code
        res *= 17
        res %= 256
    }

    return res
}