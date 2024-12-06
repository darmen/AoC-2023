package y2024.d06

import Direction
import Point2D
import println
import readInput
import runMeasure

private fun solve() {
    var input = readInput().toMutableList()

    var res = 0L

    val markers = listOf('^')
    val marker = markers.first()

    val borderX = input[0].length - 1
    val borderY = input.size - 1

    var direction = Direction.UP

    val y = input.indexOfFirst { it.contains(marker) }
    val x = input[y].indexOf(marker)

    var point = Point2D(x, y)
    val path = mutableListOf<Point2D>()
    path.add(Point2D(point.x, point.y))

    while (true) {
        val nextPoint = point.tap(direction)

        if (!(nextPoint.x <= borderX && nextPoint.y <= borderY && nextPoint.x >= 0 && nextPoint.y >= 0)) {
            break
        }

        if (input[nextPoint.y][nextPoint.x] == '#') {
            direction = direction.turnRight()
        }

        point.oneStepTowardsDirection(direction)

        if(!path.contains(point)) {
            path.add(Point2D(point.x, point.y))
        }


        input[point.y] = input[point.y].toCharArray().also { it[point.x] = 'X' }.concatToString()
        input.forEach { it.println() }
        println()
        println()
        println()

        res++
    }

    println(path.size)
}

fun main() {
    runMeasure { solve() }
}
