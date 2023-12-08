package y2023.d8.p1

import readInput

enum class Direction {
    RIGHT, LEFT
}

fun main() {
    val input = readInput(8, 1)

    var res = 0L

    val directions = input.first().toList().map {
        if (it == 'R') Direction.RIGHT else Direction.LEFT
    }

    val nodes = input.filterIndexed { index, _ ->
        index >= 2
    }.associate {
        val key = it.split(" = ").first()

        val rightPart = it.split(" = ")
            .last()
            .replace(")", "")
            .replace("(", "")
            .split(", ")

        key to Pair(rightPart.first(), rightPart.last())
    }

    var key = "AAA"

    var i = 1
    while (key != "ZZZ") {
        res++
        val d = directions[i - 1]

        key = if (d == Direction.RIGHT) nodes[key]!!.second else nodes[key]!!.first

        if (i == directions.size) i = 1 else i++
    }

    print(res)
}
