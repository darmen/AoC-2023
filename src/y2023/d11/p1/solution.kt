package y2023.d11.p1

import readInput
import kotlin.math.abs


fun main() {
    val input = readInput()

    val sMap = mutableListOf<String>()
    for (i in input.indices) {
        val s = input[i]

        sMap.add(s)
        if (!s.contains("#")) {
            sMap.add(s)
        }
    }

    val nDups = mutableListOf<Int>()
    for (i in sMap.first().indices) {
        val s = sMap.map { it[i] }.joinToString("")

        if (s.all { it == '.' }) {
            nDups.add(i)
        }
    }

    val nMap = mutableListOf<String>()
    sMap.forEach {
        var l = ""
        it.forEachIndexed { index, c ->
            l = "$l$c"
            if (nDups.contains(index)) {
                l = "$l$c"
            }
        }

        nMap.add(l)
    }

    println(nMap)


    val coords = mutableListOf<Pair<Int, Int>>()
    for (i in nMap.indices) {
        val s = nMap[i]
        for (j in s.indices) {
            if (s[j] == '#') {
                coords.add(Pair(i, j))
            }
        }
    }


    var sum = 0
    for (i in coords.indices) {
        for (j in i + 1..<coords.size) {
            val d1 = coords[i]
            val d2 = coords[j]

            if (d1.first == d2.first) {
                sum += abs(d2.second - d1.second)
                continue
            }

            if (d1.second == d2.second) {
                sum += abs(d2.first - d1.first)
                continue
            }

            sum += abs(d1.second - d2.second) + abs(d1.first - d2.first)
        }
    }

    println(sum)
}
