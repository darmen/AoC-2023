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
        var max = mutableListOf(0, 0, 0)
        val newInput = mutableListOf<String>()
        newInput.addAll(input)
        newInput.add("")

        var elfSum = 0
        for (i in newInput.indices) {
            val s = newInput[i]

            if (s == "") {
                max.add(elfSum)
                max = max.sortedDescending().take(3).toMutableList()
                elfSum = 0
            } else {
                elfSum += s.toInt()
            }
        }

        return max.sum()
    }

    part1(readInput(1, 1)).println()
    part2(readInput(1, 2)).println()
}
