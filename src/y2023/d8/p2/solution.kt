package y2023.d8.p2

import java.util.concurrent.atomic.AtomicLongArray
import readInput
import toLongArray
import kotlin.streams.asStream

enum class Direction {
    RIGHT, LEFT
}

fun process(startingKey: String, directions: List<Direction>, nodes: Map<String, Pair<String, String>>): Long {
    var key = startingKey
    var res = 0L

    var i = 1

    while (!key.contains("Z")) {
        res++
        val d = directions[i - 1]

        key = if (d == Direction.RIGHT) nodes[key]!!.second else nodes[key]!!.first

        if (i == directions.size) i = 1 else i++
    }

    return res
}

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return if (a == 0L || b == 0L) 0 else (a * b / gcd(a, b))
}

fun calculateLCM(numbers: LongArray): Long {
    if (numbers.isEmpty()) {
        return 0
    }

    var result = numbers[0]

    for (i in 1 until numbers.size) {
        result = lcm(result, numbers[i])
    }

    return result
}

fun main() {
    val input = readInput(8, 2)


    val directions = input.first().toList().map {
        if (it == 'R') Direction.RIGHT else Direction.LEFT
    }

    val nodes = input.filterIndexed { index, s ->
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

    val keys = nodes.filterKeys {
        it.endsWith("A")
    }.keys

    val positions = AtomicLongArray(keys.map { 0L }.toLongArray())

    keys.asSequence().asStream().parallel().forEach {
        val k = keys.indexOf(it)
        positions.set(k, process(it, directions, nodes))
    }

    println(calculateLCM(positions.toLongArray()))
}
