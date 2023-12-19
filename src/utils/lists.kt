package utils

fun List<String>.toIntMatrix() =
    this.map { it.toCharArray().map { char -> char.toString().toInt() }.toIntArray() }.toTypedArray()

fun Array<IntArray>.duplicate() = this.map { it.copyOf() }