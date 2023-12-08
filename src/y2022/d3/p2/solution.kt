package y2022.d3.p2

import readInput

fun main() {
    val input = readInput(3, 2)

    var res = 0

    input.chunked(3) {
        res += process(it)
    }

    println(res)
}

fun process(ss: List<String>): Int {
    val sortedSs = ss.sortedByDescending { it.length }

    val s1 = sortedSs.first()
    val s2 = sortedSs[1]
    val s3 = sortedSs.last()

    for (c in s1) {
        if (s2.contains(c) && s3.contains(c)) {
            return if (c.isLowerCase()) ('a'..'z').toList().indexOf(c) + 1 else ('A'..'Z').toList().indexOf(c) + 27
        }
    }

    return 0
}
