package y2023.d12.p1

import println
import readInput

fun main() {
    val input = readInput()

    var res = 0L

    val memo = mutableMapOf<Pair<Int, Int>, List<IntArray>>()

    for (i in input.indices) {
        var count = 0L
        val places = input[i].split(" ").first()
        val numbers = input[i].split(" ").last().split(",").map { it.toInt() }

        val acc = distributeDotsRec(places.length - numbers.sum(), numbers.size + 1, memo)
            .filter { !it.slice(1..it.size - 2).contains(0) }

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

fun distributeDotsRec(n: Int, p: Int, memo: MutableMap<Pair<Int, Int>, List<IntArray>>): List<IntArray> {
    val key = Pair(n, p)

    if (memo.containsKey(key)) {
        return memo[key]!!
    }

    val distributions = mutableListOf<IntArray>()

    // Base case
    if (p == 0) {
        if (n == 0) {
            distributions.add(IntArray(0))
        }
        return distributions
    }

    for (i in 0..n) {
        val subDistributions = distributeDotsRec(n - i, p - 1, memo)
        for (subDistribution in subDistributions) {
            distributions.add(intArrayOf(i) + subDistribution)
        }
    }

    memo[key] = distributions

    return distributions
}