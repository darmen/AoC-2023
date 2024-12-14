package com.heydarmen.adventofcode

import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.atomic.AtomicLongArray
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String = "input") = Path("src/$name.txt").readLines()
fun readText(name: String = "input") = Path("src/$name.txt").readText()
fun readInput(): String = object {}.javaClass.getResource("/input.txt")?.readText()!!

fun readInput(dayNumber: Int) = readInput("Day${dayNumber.toString().padStart(2, '0')}")
fun readInput(dayNumber: Int, part: Int) = readInput("Day${dayNumber.toString().padStart(2, '0')}_$part")

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun AtomicLongArray.toLongArray(): LongArray {
    return LongArray(this.length()) { this[it] }
}

fun List<String>.nthColumn(n: Int): String = this.map { it[n] }.joinToString("")
fun Long.toBigInteger() = BigInteger.valueOf(this)
fun Int.toBigInteger() = BigInteger.valueOf(toLong())
infix fun Int.`**`(exponent: Int): Int = toDouble().pow(exponent).toInt()

inline fun runMeasure(block: () -> Unit) {
    measureTimeMillis(block).also {
        "Execution time: $it ms".println()
    }
}

fun String.setCharAt(i: Int, c: Char): String {
    return if (i in this.indices) {
        this.toCharArray().also { it[i] = c }.joinToString("")
    } else {
        this
    }
}

fun String.setStringCharAt(i: Int, c: String): String {
    return if (i in this.indices) {
        this.toCharArray().also { it[i] = c[0] }.joinToString("")
    } else {
        this
    }
}

fun <T> Sequence<T>.takeNextWhile(predicate: (T) -> Boolean): Sequence<T> = Sequence {
    val iter = iterator()
    var hasNext = iter.hasNext()
    object : Iterator<T> {
        override fun hasNext() = hasNext
        override fun next(): T {
            val n = iter.next()
            return n.also {
                hasNext = iter.hasNext() && predicate(it)
            }
        }
    }
}

fun List<Long>.lcm(): Long = this.reduce { acc, num ->
    abs(acc * num) / (acc to num).gcd()
}

fun Pair<Long, Long>.gcd(): Long = if (second == 0L) first else (second to first % second).gcd()
fun Pair<Int, Int>.gcd(): Int = if (second == 0) first else (second to first % second).gcd()

typealias Point = Pair<Int, Int>
typealias PointLong = Pair<Long, Long>
typealias IntList = List<Int>

data class Point2D(var x: Int, var y: Int) {
    fun neighbors(): Set<Point2D> = buildSet {
        this.addAll(cardinalNeighbors())

        for (r in arrayOf(-1, 1)) {
            for (c in arrayOf(-1, 1)) {
                this.add(
                    Point2D(x + r, y + c)
                )
            }
        }
    }

    fun oneStepTowardsDirection(direction: Direction) {
        x += direction.d.first
        y += direction.d.second
    }

    fun tap(direction: Direction): Point2D {
        return Point2D(x + direction.d.first, y + direction.d.second)
    }

    fun cardinalNeighbors(): List<Point2D> {
        return Direction.entries.map {
            Point2D(x + it.d.first, y + it.d.second)
        }
    }

    fun manhattanDistance(other: Point2D): Int {
        return abs(this.x - other.x) + abs(this.y + other.y)
    }

    fun distance(other: Point2D): Double {
        return sqrt(((other.x - this.x) * (other.x - this.x) + (other.y - this.y) * (other.x - this.x)).toDouble())
    }

    operator fun plus(other: Point2D): Point2D {
        return Point2D(x + other.x, y + other.y)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Point2D) {
            return this.x == other.x && this.y == other.y
        }

        return false
    }
}

enum class Direction(val d: Pair<Int, Int>) {
    LEFT(-1 to 0), RIGHT(1 to 0), UP(0 to -1), DOWN(0 to 1);

    fun reversed(): Direction =
        when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }

    fun turnRight(): Direction =
        when (this) {
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
        }
}

fun Array<CharArray>.isSafe(point: Point2D): Boolean = point.x in indices && point.y in 0..<this.first().size

// Rotates list of strings to the left
fun rotateLeft(matrix: List<String>): List<String> {
    val rows = matrix.size
    val cols = matrix[0].length
    val rotated = MutableList(cols) { StringBuilder() }

    for (col in cols - 1 downTo 0) {
        for (row in 0 until rows) {
            rotated[cols - 1 - col].append(matrix[row][col])
        }
    }

    return rotated.map { it.toString() }
}

// Extracts diagonals (top-left to bottom-right and top-right to bottom-left) from a list of strings
fun extractDiagonals(matrix: List<String>): List<String> {
    val n = matrix.size
    val diagonals = mutableListOf<String>()

    for (d in 0 until 2 * n - 1) {
        val diagonal = StringBuilder()
        for (row in 0 until n) {
            val col = d - row
            if (col in 0 until n) {
                diagonal.append(matrix[row][col])
            }
        }
        diagonals.add(diagonal.toString())
    }

    for (d in 0 until 2 * n - 1) {
        val diagonal = StringBuilder()
        for (row in 0 until n) {
            val col = row + d - (n - 1)
            if (col in 0 until n) {
                diagonal.append(matrix[row][col])
            }
        }
        diagonals.add(diagonal.toString())
    }

    return diagonals
}

// Counts occurrences
fun countOccurrences(text: String, substring: String): Int {
    if (substring.isEmpty()) return 0
    return text.windowed(substring.length, 1).count { it == substring }
}

