package y2022.d6.p1

import readInput

fun main() {
    val input = readInput()
    val s = input.first()

    for (i in 3..<s.length) {
        val substring = s.substring(i - 3, i + 1)

        if (substring.isMarker()) {
            println(i + 1)
            break
        }
    }
}

fun String.isMarker(): Boolean = this.map { it.code }.toSet().size == 4
