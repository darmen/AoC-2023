package y2023.d12.p1

import println
import readInput

fun main() {
    val input = readInput()

    var res = 0L

    for (i in input.indices) {
        var count = 0L
        val places = input[i].split(" ").first()
        val numbers = input[i].split(" ").last().split(",").map { it.toInt() }

        val maxLen = places.length
        val numbersLen = numbers.sum()
        val dotsInitialCount = numbers.size - 1
        val dots = intArrayOf(0, *(IntArray(dotsInitialCount) { 1 }), 0)
        val missingDotsNumber = maxLen - numbersLen - dotsInitialCount

        val acc = mutableListOf<IntArray>()
        distributeDotsRec(maxLen - numbersLen, dots.size, dots, 0, acc)

// 1 2 0 1
        for (j in acc.indices) {
            val s = acc[j]
            var t = ""
            for (k in s.indices) {
                val a = acc[j][k]
                t += ".".repeat(a)
                if (k < numbers.size) t += "#".repeat(numbers[k])
            }


            var c = 0
            for (l in t.indices) {
                val place = if (places[l] == '?') t[l] else places[l]
                if (place == t[l]) {
                    c++
                }
            }

            if (c == t.length) {
                count++
                println(t)
//
            }
        }

        res += count

//        acc.forEachIndexed { index, ints ->
//            if (index % 2 == 0) {
//                s += ".".repeat(ints[index])
//            } else {
//                s += numbers[index / 2]
//            }
//        }

    }

    res.println()
}

fun distributeDotsRec(n: Int, p: Int, distribution: IntArray, currentIndex: Int, acc: MutableList<IntArray>) {
    if (currentIndex == p) {
        if (!distribution.slice(1..distribution.size-2).contains(0)) {
            if (distribution.sum() == n) {
                acc.add(distribution.copyOf())
            }
        }

        return
    }

    for (i in 0..n) {
        distribution[currentIndex] = i
        distributeDotsRec(n, p, distribution, currentIndex + 1, acc)
    }
}