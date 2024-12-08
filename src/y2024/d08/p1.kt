package y2024.d08

import Point2D
import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput()
    val bounds = input[0].length - 1 to input.size - 1
    val antennas = mutableSetOf<Pair<Point2D, Char>>()
    val antinodes = mutableSetOf<Point2D>()

    input.forEachIndexed { r, row ->
        row.forEachIndexed { c, char ->
            if (char != '.') {
                val antenna = Point2D(c, r) to char
                antennas.add(antenna)
            }
        }
    }

    val groupedAntennas = antennas.groupBy { it.second }

    groupedAntennas.forEach { (_, l) ->
        val list = l.map { it.first }
        list.println()

        for (a in list) {
            for (b in list) {
                if (a == b) continue

                val dX = b.x - a.x
                val dY = b.y - a.y

                val pointB = Point2D(b.x + dX, b.y + dY)
                val pointA = Point2D(a.x - dX, a.y - dY)

                if (pointA.x in (0..bounds.first) && pointA.y in (0..bounds.second)) {
                    antinodes.add(pointA)
                }

                if (pointB.x in (0..bounds.first) && pointB.y in (0..bounds.second)) {
                    antinodes.add(pointB)
                }
            }
        }
    }

    antinodes.size.println()
}

fun main() {
    runMeasure { solve() }
}
