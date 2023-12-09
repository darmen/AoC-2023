package y2022.d6.p2

import readInput

fun main() {
    val input = readInput()
    val s = input.first()

    for (i in 13..<s.length) {
        val substring = s.substring(i - 13, i + 1)

        if (substring.isMarker()) {
            println(i + 1)
            break
        }
    }
}

fun String.isMarker(): Boolean = this.map { it.code }.toSet().size == 14
