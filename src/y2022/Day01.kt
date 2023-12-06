package y2022

import println
import readInput
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        var max = 0

        var elfSum = 0
        for (i in input.indices) {
            val s = input[i]

            if (s == "") {
                max = max(elfSum, max)
                elfSum = 0
            } else {
                elfSum += s.toInt()
            }
        }

        return max
    }

    fun part2(input: List<String>): Int {
        var sum = 0


        return sum
    }

    part1(readInput(1, 1)).println()
//    part2(readInput(1, 2)).println()
}
