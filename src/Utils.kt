import java.io.Serializable
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.atomic.AtomicLongArray
import kotlin.Pair
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.math.pow
import kotlin.system.measureTimeMillis

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String = "input") = Path("src/$name.txt").readLines()
fun readText(name: String = "input") = Path("src/$name.txt").readText()

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

data class Triple<out A, out B, out C>(
    val first: A,
    val second: B,
    val third: C
) : Serializable {
    override fun toString(): String = "($first, $second, $third)"
}

typealias Point = Pair<Int, Int>
typealias PointLong = Pair<Long, Long>
typealias IntList = List<Int>