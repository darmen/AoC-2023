package y2023.d13.p2

import println
import readText
import kotlin.math.min
import y2023.d13.p1.findH as findH1
import y2023.d13.p1.findV as findV1

fun main() {
    val input = readText().split("\n\n")

    var res = 0L

    for (i in input.indices) {
        val it = input[i]
        val t = it.split("\n")

        val v1 = findV1(t)
        val h1 = findH1(t)

        val v = findV(t, v1)?.second ?: 0
        val h = findH(t, h1)?.second ?: 0

        val sum = v + 100 * h
        res += sum

//        println(it)
//        println("$v $h")
//        println("")
    }

    res.println()
}

fun List<String>.nthColumn(n: Int) = this.map { it[n] }

fun findV(s: List<String>, pairToIgnore: Pair<Int, Int>?): Pair<Int, Int>? {
    val lors = mutableListOf<Pair<Int, Int>>()

    for (i in 1..<s.first().length) {

        val stepsLeft = 0..<i
        val stepsRight = i..<i + s.first().length - i

        val minSteps = min(stepsLeft.toList().size, stepsRight.toList().size)
        var width = 0

        for (j in 1..minSteps) {
            val col1 = s.nthColumn(i - j)
            val col2 = s.nthColumn(i + j - 1)
            val p = col1.zip(col2).count {
                it.first != it.second
            }
            if (p <= 1) {
                width++
            } else {
                break
            }
        }

        if (width == 0) continue

        if (i + width == s.first().length || i == width) {
            if (width to i != pairToIgnore) {
                lors.add(width to i)
            }
        }
    }

    return if (lors.isEmpty()) {
        null
    } else {
        lors.sortedWith(compareBy({ it.first }, { it.second })).asReversed().first()
    }
}

fun findH(s: List<String>, pairToIgnore: Pair<Int, Int>?): Pair<Int, Int>? {
    val lors = mutableListOf<Pair<Int, Int>>()

    for (i in 1..<s.size) {

        val stepsLeft = 0..<i
        val stepsRight = i..<i + s.size - i

        val minSteps = min(stepsLeft.toList().size, stepsRight.toList().size)
        var width = 0
        for (j in 1..minSteps) {
            val col1 = s[i - j]
            val col2 = s[i + j - 1]

            val p = col1.zip(col2).count {
                it.first != it.second
            }

            if (p <= 1) {
                width++
            } else {
                break
            }
        }

        if (width == 0) continue

        if (i + width == s.size || i == width) {
            if (width to i != pairToIgnore) {
                lors.add(width to i)
            }
        }
    }

    return if (lors.isEmpty()) {
        null
    } else {
        lors.sortedWith(compareBy({ it.first }, { it.second })).asReversed().first()
    }
}