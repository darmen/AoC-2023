package y2023.d17.p2

import Point
import java.util.PriorityQueue
import println
import readInput
import runMeasure
import utils.toIntMatrix

fun main() {
    runMeasure {
        solve()
    }
}

fun solve() {
    val input = readInput().toIntMatrix()
    val visited = mutableSetOf<SeenItem>()

    val queue = PriorityQueue<QueueItem>(1, compareBy { it.heatLoss })
    queue.add(QueueItem(0, 0 to 0, Direction.STAY, 0))

    while (queue.isNotEmpty()) {
        val item = queue.poll()

        val n = item.steps

        if (item.point.first == input.size - 1 && item.point.second == input[0].size - 1 && n >= 4) {
            item.heatLoss.println()
            break
        }

        val seenItem = SeenItem(item.point, item.dir, item.steps)

        if (visited.contains(seenItem)) {
            continue
        }

        visited.add(seenItem)

        if (n < 10 && !(item.dir.delta.first == 0 && item.dir.delta.second == 0)) {
            val nr = item.point.first + item.dir.delta.first
            val nc = item.point.second + item.dir.delta.second

            if (nr in input.indices && nc in 0..<input[0].size) {
                queue.offer(
                    QueueItem(item.heatLoss + input[nr][nc], nr to nc, item.dir, n + 1)
                )
            }
        }

        if (n >= 4 || (item.dir.delta.first == 0 && item.dir.delta.second == 0)) {
            for (newDir in Direction.entries.filter { it != Direction.STAY }) {
                if ((newDir.delta.first != item.dir.delta.first || newDir.delta.second != item.dir.delta.second) && (newDir.delta.first != -item.dir.delta.first || newDir.delta.second != -item.dir.delta.second)) {
                    val nr = item.point.first + newDir.delta.first
                    val nc = item.point.second + newDir.delta.second

                    if (nr in input.indices && nc in input[0].indices) {
                        queue.offer(
                            QueueItem(item.heatLoss + input[nr][nc], nr to nc, newDir, 1)
                        )
                    }
                }
            }
        }

    }
}

private data class SeenItem(
    val point: Point,
    val dir: Direction,
    val steps: Int
)

private data class QueueItem(
    val heatLoss: Int,
    val point: Point,
    val dir: Direction,
    val steps: Int
)

private enum class Direction(val delta: Pair<Int, Int>) {
    UP(-1 to 0), DOWN(1 to 0), LEFT(0 to -1), RIGHT(0 to 1), STAY(0 to 0);
}