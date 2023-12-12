package y2023.d12.p2

import println
import readInput
import utils.Memo
import utils.memoize

data class Key(val places: List<Char>, val groups: List<Int>)

val f = Memo<Key, Long>::process.memoize()

fun Memo<Key, Long>.process(key: Key): Long {
    val places = key.places
    val groups = key.groups

    if (places.isEmpty()) return if (groups.isEmpty()) 1 else 0
    if (groups.isEmpty()) return if (places.contains('#')) 0 else 1

    val fP = places.first()
    val fG = groups.first()

    val cLeft =
        if (fP != '#') f(Key(places.drop(1), groups)) else 0

    val cRight = when {
        fG > places.size -> 0
        places.take(fG).any { it == '.' } -> 0
        places.size != fG && places[fG] == '#' -> 0
        fP == '.' -> 0
        else -> f(Key(places.drop(fG + 1), groups.drop(1)))
    }

    return cLeft + cRight
}

fun main() {
    var res = 0L

    val input = readInput()

    for (i in input.indices) {
        val places = List(5) { _ -> input[i].split(" ").first() }
            .joinToString("?")
            .toList()

        val numbers = List(5) { _ -> input[i].split(" ").last() }
            .joinToString(",")
            .split(",")
            .map { s -> s.toInt() }

        res += f(Key(places, numbers))
    }

    res.println()
}