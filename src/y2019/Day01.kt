package y2019

import println
import readInput

class Day01(val input: List<String>) {
    fun solve1(): Any {
        return input.sumOf {
            it.toInt() / 3 - 2
        }
    }

    fun solve2(): Any {
        return buildList {
            input.forEach {
                var fuel = it.toInt() / 3 - 2

                do {
                    this.add(fuel)
                    fuel = fuel / 3 - 2
                } while (fuel > 0)
            }
        }.sum()
    }
}

fun main() {
    val day = Day01(readInput())

    day.solve1().println()
    day.solve2().println()
}