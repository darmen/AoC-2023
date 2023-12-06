package y2023

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val first = it.first { it.isDigit() }
            val last = it.reversed().first { it.isDigit() }

            val number = "${first.toString()}${last.toString()}"
            sum += number.toInt()
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        val wordToDigit = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        input.forEach { s ->
            val wordsFirstPositions = mutableMapOf(
                "1" to -1,
                "2" to -1,
                "3" to -1,
                "4" to -1,
                "5" to -1,
                "6" to -1,
                "7" to -1,
                "8" to -1,
                "9" to -1,
                "one" to -1,
                "two" to -1,
                "three" to -1,
                "four" to -1,
                "five" to -1,
                "six" to -1,
                "seven" to -1,
                "eight" to -1,
                "nine" to -1
            )

            val wordsLatestPositions = mutableMapOf(
                "1" to -1,
                "2" to -1,
                "3" to -1,
                "4" to -1,
                "5" to -1,
                "6" to -1,
                "7" to -1,
                "8" to -1,
                "9" to -1,
                "one" to -1,
                "two" to -1,
                "three" to -1,
                "four" to -1,
                "five" to -1,
                "six" to -1,
                "seven" to -1,
                "eight" to -1,
                "nine" to -1
            )

            wordsFirstPositions.keys.forEach { word ->
                wordsFirstPositions[word] = s.indexOf(word)
            }

            wordsLatestPositions.keys.forEach { word ->
                wordsLatestPositions[word] = s.lastIndexOf(word)
            }

            var first = wordsFirstPositions.filterNot { it.value == -1 }.minBy { it.value }.key
            var last = wordsLatestPositions.filterNot { it.value == -1 }.maxBy { it.value }.key

            if (first.length > 1) {
                first = wordToDigit[first]!!
            }

            if (last.length > 1) {
                last = wordToDigit[last]!!
            }

            sum += "$first$last".toInt()
        }

        return sum
    }

    part1(readInput(1, 1)).println()
    part2(readInput(1, 2)).println()
}
