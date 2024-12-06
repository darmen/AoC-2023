package y2024.d06

import Direction
import Point2D
import println
import readInput
import runMeasure

private fun solve() {
    var input = readInput().toMutableList()

    val stones = mutableSetOf<Point2D>()
    input.forEachIndexed { r, s ->
        s.forEachIndexed { c, el ->
            if (el == '#') {
                stones.add(Point2D(c, r))
            }
        }
    }


    var res = 0L

    val markers = listOf('^')
    val marker = markers.first()

    val borderX = input[0].length - 1
    val borderY = input.size - 1

    var direction = Direction.UP

    val y = input.indexOfFirst { it.contains(marker) }
    val x = input[y].indexOf(marker)

    var point = Point2D(x, y)

    point.oneStepTowardsDirection(direction)

    val path = mutableListOf<Point2D>()

    path.add(Point2D(point.x, point.y))

    val os = mutableSetOf<Point2D>()

    while (true) {
        val nextPoint = point.tap(direction)

        if (!(nextPoint.x <= borderX && nextPoint.y <= borderY && nextPoint.x >= 0 && nextPoint.y >= 0)) {
            break
        }

        if (stones.contains(nextPoint)) {
            direction = direction.turnRight()
            continue
        }


        if (hasLoop(stones + nextPoint, input, point.x, point.y, direction)) {
            os.add(nextPoint)
        }

        point.oneStepTowardsDirection(direction)

    }

    println(os.size)
}

fun hasLoop(obstructions: Set<Point2D>, input: MutableList<String>, x: Int, y: Int, d: Direction): Boolean {
    var direction = d
    val borderX = input[0].length - 1
    val borderY = input.size - 1

    val point = Point2D(x, y)

    val path = mutableSetOf<Pair<Point2D, Direction>>()
    path.add(Point2D(point.x, point.y) to d)

    while (true) {
        val nextPoint = point.tap(direction)

        if (!(nextPoint.x <= borderX && nextPoint.y <= borderY && nextPoint.x >= 0 && nextPoint.y >= 0)) {
            break
        }

        if (obstructions.contains(nextPoint)) {
            direction = direction.turnRight()
        }

        point.oneStepTowardsDirection(direction)

        if(!path.contains(point to direction)) {
            path.add(Point2D(point.x, point.y) to direction)
        } else {
            return true
        }
    }

    return false

}

fun main() {
    runMeasure { solve() }
}
