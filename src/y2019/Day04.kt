package y2019

import println
import readInput
import runMeasure

class Day04(val input: List<String>) {
    private val range = input.first().split("-").first().toInt()..input.first().split("-").last().toInt()

    fun solve1(): Any {
        var result = 0
        for (i in range) {
            if (digitsNeverDecrease(i) && adjacentDigitsAreTheSame(i)) {
                result++
            }
        }

        return result
    }

    private fun digitsNeverDecrease(n: Int): Boolean {
        var result = true
        val s = n.toString()

        for (i in 1..<s.length) {
            if (s[i] < s[i - 1]) {
                result = false
                break
            }
        }

        return result
    }

    private fun adjacentDigitsAreTheSame(n: Int): Boolean {
        var result = false
        val s = n.toString()

        for (i in 1..<s.length) {
            if (s[i] == s[i - 1]) {
                result = true
                break
            }
        }

        return result
    }

    fun solve2(): Any {
        TODO("Not solved yet")
    }
}

fun main() {
    runMeasure {
        val day = Day04(readInput())
        day.solve1().println()
        day.solve2().println()
    }
}
