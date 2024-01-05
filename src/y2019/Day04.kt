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

    private fun adjacentDigitsNotPartOfLargerGroup(n: Int): Boolean {
        val s = n.toString()
        val groupLengths = mutableListOf<Int>()
        var l = 1
        for (i in 1..<s.length) {
            val c = s[i]
            val cp = s[i - 1]

            if (c == cp) {
                l++
            } else {
                groupLengths.add(l)
                l = 1
            }

            if (i == s.length - 1) {
                groupLengths.add(l)
            }
        }

        return groupLengths.contains(2)
    }

    fun solve2(): Any {
        var result = 0
        for (i in range) {
            if (digitsNeverDecrease(i) && adjacentDigitsNotPartOfLargerGroup(i)) {
                result++
            }
        }

        return result
    }
}

fun main() {
    runMeasure {
        val day = Day04(readInput())
        day.solve1().println()
        day.solve2().println()
    }
}
