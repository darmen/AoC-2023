package y2023

import println
import readInput

fun main() {

    data class Race(val holdTime: Long, val moveTime: Long)

    fun part1(input: List<String>): Int {
        var result = 1

        val times = input.first().split(":").last().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        val distances = input.last().split(":").last().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }

        for (i in times.indices) {
            var ways = 0
            val t = times[i]
            val d = distances[i]

            for (j in 1..t) {
                if (j * (t-j) > d) {
                    ways += 1
                }
            }

            result *= ways
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 1

        val times = input.first().replace(" ","").split(":").last().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        val distances = input.last().replace(" ","").split(":").last().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }

        for (i in times.indices) {
            var ways = 0
            val t = times[i]
            val d = distances[i]

            for (j in 1..t) {
                if (j * (t-j) > d) {
                    ways += 1
                }
            }

            result *= ways
        }

        return result
    }



    part1(readInput(6, 1)).println()
    part2(readInput(6, 2)).println()
}
