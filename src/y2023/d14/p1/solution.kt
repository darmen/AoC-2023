package y2023.d14.p1

import nthColumn
import println
import readInput

enum class D {
    N, S, W, E
}

fun main() {
    val input = readInput()

    var res = 0L

    for (i in 0..<input.first().length) {
        res += process(input.nthColumn(i))
    }

    res.println()
}

fun process(s: String): Int {
    val parts = s.split("#")

    val p = parts.joinToString("#") {
        var r = "0".repeat(it.count { c -> c == 'O' })
        r += ".".repeat(it.count { c -> c == '.' })
        r
    }

    return p.mapIndexed { index, c ->
        if (c == '0') p.length - index else 0
    }.sum()
}