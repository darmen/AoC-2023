package y2022.d3.p1

import readInput

fun main() {
    val input = readInput(3, 1)

    var res = 0

    for (i in input.indices) {
        res += process(input[i])
    }

    println(res)
}

fun process(s: String): Int {
    val part1 = s.substring(0, s.length / 2)
    val part2 = s.substring(s.length / 2, s.length)

    for (c in part1) {
        if (part2.contains(c)) {
            return if (c.isLowerCase()) ('a'..'z').toList().indexOf(c) + 1 else ('A'..'Z').toList().indexOf(c) + 27
        }
    }

    return 0
}
