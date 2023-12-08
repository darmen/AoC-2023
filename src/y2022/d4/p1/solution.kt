package y2022.d4.p1

import readInput

fun main() {
    val input = readInput(4, 1)

    var res = 0

    for (i in input.indices) {
        val elves = input[i].split(",")

        val first = elves[0].split("-").first().toInt()..elves[0].split("-").last().toInt()
        val second = elves[1].split("-").first().toInt()..elves[1].split("-").last().toInt()

        if (first.intersect(second).size == first.count() || first.intersect(second).size == second.count()) {
            res++
        }
    }

    println(res)
}