package y2023.d22.p1

import println
import readInput
import runMeasure
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private fun solve() {
    val input = readInput()

    val bricks = mutableListOf<Brick>()

    for (i in input.indices) {
        val s = input[i]
        val (x1, y1, z1) = s.split("~").first().split(",").map { it.toInt() }
        val (x2, y2, z2) = s.split("~").last().split(",").map { it.toInt() }

        bricks.add(
            Brick(
                i.toString(), Point(x1, y1, z1), Point(x2, y2, z2)
            )
        )
    }

    val sortedByAirPosition = bricks.sortedBy {
        listOf(it.first.third, it.second.third).min()
    }

    val plane =
        bricks.maxOf { max(it.first.first, it.second.first) } + 1 to
                bricks.maxOf { max(it.first.second, it.second.second) } + 1

    val map = Array(plane.first) { Array(plane.second) { Tile() } }

    for (b in sortedByAirPosition) {
        val maxHeight = b.landingPoints.maxOf {
            map[it.first][it.second].height
        }

        for (l in b.landingPoints) {
            if (map[l.first][l.second].brick != null) {
                if (map[l.first][l.second].height == maxHeight) {
                    map[l.first][l.second].brick!!.supports.add(b.letter)
                }
            }

            map[l.first][l.second] = Tile(maxHeight + b.height, b)
        }
    }

    bricks.count { br ->
        if (br.supports.size == 0) {
            true
        } else {
            br.supports.all { s ->
                bricks.filter { it.letter != br.letter }
                    .any { it.supports.contains(s) }
            }
        }
    }.println()
}

fun main() {
    runMeasure { solve() }
}

data class Brick(
    val letter: String,
    val first: Point,
    val second: Point,
    val supports: MutableSet<String> = mutableSetOf()
) {
    var height = 0

    private val xRange = min(this.first.first, this.second.first)..max(this.first.first, this.second.first)
    private val yRange = min(this.first.second, this.second.second)..max(this.first.second, this.second.second)

    var landingPoints: List<Pair<Int, Int>>

    init {
        height = abs(first.third - second.third) + 1

        val lp = mutableListOf<Pair<Int, Int>>()
        for (x in xRange) {
            for (y in yRange) {
                lp.add(x to y)
            }
        }
        landingPoints = lp.toList()
    }
}

typealias Point = Triple<Int, Int, Int>

data class Tile(val height: Int = 0, val brick: Brick? = null)