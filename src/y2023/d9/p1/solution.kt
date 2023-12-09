package y2023.d9.p1

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
        return l.last()
    }

    val d = mutableListOf<Int>()

    for (i in 1..<l.size) {
        d.add(l[i] - l[i - 1])
    }

    val newLastValue = l.last() + process(d)
    l.add(newLastValue)

    return newLastValue
}

