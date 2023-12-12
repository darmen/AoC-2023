package y2023.d12.p2

import println
import readInput
import utils.Memo
import utils.memoize

fun main() {
    val input = readInput()

    var res = 0L

    for (i in input.indices) {
        var count = 0L

        val places = List(5) { input[i].split(" ").first() }.joinToString(",")

        val numbers = List(5) { input[i].split(" ").last() }
            .joinToString(",")
            .split(",")
            .map { it.toInt() }

        val acc = Memo<Pair<Int, Int>, List<IntArray>>::process
            .memoize()
            .invoke(
                places.length - numbers.sum() to numbers.size + 1
            )
            .filter {
                !it.slice(1..it.size - 2).contains(0)
            }

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
            }
        }

        res += count
    }

    res.println()
}

fun Memo<Pair<Int, Int>, List<IntArray>>.process(key: Pair<Int, Int>): List<IntArray> {
    val distributions = mutableListOf<IntArray>()

    val n = key.first
    val p = key.second

    if (p == 0) {
        if (n == 0) {
            distributions.add(IntArray(0))
        }
        return distributions
    }

    for (i in 0..n) {
        val subDistributions = recurse(n - i to p - 1)
        for (subDistribution in subDistributions) {
            distributions.add(intArrayOf(i) + subDistribution)
        }
    }

    return distributions
}
