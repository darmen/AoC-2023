package y2023.d21.p2

import Point2D
import isSafe
import println
import readInput
import runMeasure

// Sketch – https://virtual-graph-paper.com/NzYxZWVhMTAwYzg4
// Greatly inspired by https://github.com/tginsberg/, thanks!
fun draw(input: Array<CharArray>, maxSteps: Int) = buildMap {
    val center = Point2D((input[0].size - 1) / 2, (input.size - 1) / 2)

    val queue = ArrayDeque<Pair<Point2D, Distance>>().apply {
        add(center to 0)
    }

    while (queue.isNotEmpty()) {
        queue.removeFirst().let { (location, distance) ->
            if (location !in this && distance <= maxSteps) {
                this[location] = distance

                queue.addAll(
                    location.cardinalNeighbors()
                        .filter { it !in this }
                        .filter { input.isSafe(it) }
                        .filter { input[it.y][it.x] != '#' }
                        .map { it to distance + 1 }
                )
            }
        }
    }
}

/**
 * @see "y20023/d21/graph.png" for my visualized thoughts
 */
fun solve() {
    val input = readInput().map { it.toCharArray() }.toTypedArray()

    val desiredSteps = 26501365
    val steps = draw(input, desiredSteps)

    val oddCorners = steps.count { it.value % 2 == 1 && it.value > 65 }.toLong()
    val evenCorners = steps.count { it.value % 2 == 0 && it.value > 65 }.toLong()
    val evenBlock = steps.values.count { it % 2 == 0 }.toLong()
    val oddBlock = steps.values.count { it % 2 == 1 }.toLong()
    val n: Long = ((desiredSteps.toLong() - (input.size / 2)) / input.size)

    val even: Long = n * n
    val odd: Long = (n + 1) * (n + 1)

    ((odd * oddBlock) + (even * evenBlock) - ((n + 1) * oddCorners) + (n * evenCorners))
        .println()
}

fun main() {
    runMeasure { solve() }
}

typealias Distance = Int
