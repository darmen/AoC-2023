package y2023.d24.p1

import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput()

    var res = 0L

    val hailstones = input.map {
        val first = it.split(" @ ").first()
        val second = it.split(" @ ").last()

        val (x, y, z) = first.split(", ").map { t -> t.replace(" ", "").toLong() }
        val (sx, sy, sz) = second.split(", ").map { t -> t.replace(" ", "").toInt() }

        Hailstone(x, y, z, sx, sy, sz)
    }

    for (i in hailstones.indices) {
        for (j in i + 1..<hailstones.size) {
            val h1 = hailstones[i]
            val h2 = hailstones[j]
            h1.intersection(h2)?.let {
                if (it.first in xb.first..xb.second && it.second in xb.first..xb.second) {
                    val checkResult = listOf(hailstones[i], hailstones[j]).all { h ->
                        // hailstones has to be "behind" intersection point

                        val xCheck = if (h.xs > 0) {
                            it.first > h.x
                        } else {
                            it.first < h.x
                        }

                        val yCheck = if (h.ys > 0) {
                            it.second > h.y
                        } else {
                            it.second < h.y
                        }

                        xCheck && yCheck
                    }

                    if (checkResult) res++
                }
            }
        }
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}

val xb = 200000000000000.0 to 400000000000000.0
//val xb = 200000000000000.0 to 400000000000000.0

private data class Hailstone(
    val x: Long,
    val y: Long,
    val z: Long,
    val xs: Int,
    val ys: Int,
    val zs: Int
) {
    var k: Double = 0.0
    var b: Double = 0.0

    init {
        val x1 = (x + xs).toDouble()
        val y1 = (y + ys).toDouble()

        k = ((y1 - y) / (x1 - x)).toDouble()
        b = y - k * x
    }

    fun intersection(other: Hailstone): Pair<Double, Double>? {
        if (this.k == other.k) return null

        val x = (other.b - this.b) / (this.k - other.k)
        val y = this.k * x + this.b

        return x to y
    }
}