package y2023.d9.p2

import println
import readInput

fun main() {
    val input = readInput()

    var res = 0L

    for (i in input.indices) {
        val s = input[i]
        val l = s.split(" ").map { it.toInt() }.toMutableList()
        val p = process(l)
        res +=  p
    }

    res.println()
}

fun process(l: MutableList<Int>): Int {
    if (l.toSet().size == 1) {
        return l.first()
    }

    val d = mutableListOf<Int>()

    for (i in 1..<l.size) {
        d.add(l[i] - l[i - 1])
    }

    val newFirstValue = l.first() - process(d)
    l.add(0, newFirstValue)

    return newFirstValue
}

