package y2023.d10.p1

import println
import readInput

val directionToDelta = mapOf(
    Direction.D to Pair(0, 1),
    Direction.U to Pair(0, -1),
    Direction.L to Pair(-1, 0),
    Direction.R to Pair(1, 0),
)

val directionToPossibleTiles = mapOf(
    Direction.D to listOf(Tile.VERTICAL, Tile.DR, Tile.DL),
    Direction.U to listOf(Tile.VERTICAL, Tile.UL, Tile.UL),
    Direction.R to listOf(Tile.HORIZONTAL, Tile.DL, Tile.UL),
    Direction.L to listOf(Tile.HORIZONTAL, Tile.DR, Tile.UR),
)

val directionToTiles = mapOf(
    Direction.D to mapOf(
        Tile.VERTICAL to (Pair(0, 1) to Direction.D),
        Tile.DR to (Pair(1, 0) to Direction.R),
        Tile.DL to (Pair(-1, 0) to Direction.L),
    ),
    Direction.U to mapOf(
        Tile.VERTICAL to (Pair(0, -1) to Direction.U),
        Tile.UR to (Pair(1, 0) to Direction.R),
        Tile.UL to (Pair(-1, 0) to Direction.L),
    ),
    Direction.L to mapOf(
        Tile.HORIZONTAL to (Pair(-1, 0) to Direction.L),
        Tile.UR to (Pair(0, 1) to Direction.D),
        Tile.DR to (Pair(0, -1) to Direction.U),
    ),
    Direction.R to mapOf(
        Tile.HORIZONTAL to (Pair(1, 0) to Direction.R),
        Tile.DL to (Pair(0, -1) to Direction.U),
        Tile.UL to (Pair(0, 1) to Direction.D),
    ),
)

fun main() {
    val input = readInput()

    val sizeX = input.first().length
    val sizeY = input.size

    val map: Array<Array<Tile>> = Array(sizeX) { Array(sizeY) { Tile.GROUND } }

    var startX = -1
    var startY = -1

    for (i in input.indices) {
        val s = input[i]

        for (j in s.indices) {
            val c = s[j].toString()
            map[j][i] = Tile.entries.first { it.v == c }

            if (c == "S") {
                startX = j
                startY = i
            }
        }
    }

    var currentX = startX
    var currentY = startY
    var direction = Direction.R

    // right
    if (currentX < sizeX) {
        if (directionToPossibleTiles[Direction.R]!!.contains(map[currentX + 1][currentY])) {
            direction = Direction.R
        }
    }

    // left
    if (currentX > 0) {
        if (directionToPossibleTiles[Direction.L]!!.contains(map[currentX - 1][currentY])) {
            direction = Direction.L
        }
    }

    // up
    if (currentY > 0) {
        if (directionToPossibleTiles[Direction.U]!!.contains(map[currentX][currentY - 1])) {
            direction = Direction.U
        }
    }

    // down
    if (currentY < sizeY) {
        if (directionToPossibleTiles[Direction.D]!!.contains(map[currentX][currentY + 1])) {
            direction = Direction.D
        }
    }

    currentX += directionToDelta[direction]!!.first
    currentY += directionToDelta[direction]!!.second

    var res = 0

    var nextTile: Tile?

    while (true) {
        res++
        nextTile = map[currentX][currentY]

        if (nextTile == Tile.START) break

        val newCoords = directionToTiles[direction]!![nextTile]!!.first
        val newDirection = directionToTiles[direction]!![nextTile]!!.second

        currentX += newCoords.first
        currentY += newCoords.second
        direction = newDirection
    }

    (res / 2).println()
}


enum class Direction { D, U, L, R }
enum class Tile(val v: String) {
    START("S"), GROUND("."), HORIZONTAL("-"), VERTICAL("|"), DR("L"), DL("J"), UL("7"), UR("F");
}