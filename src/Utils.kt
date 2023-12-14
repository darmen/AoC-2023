import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.atomic.AtomicLongArray
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

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