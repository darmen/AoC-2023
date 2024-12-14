package com.heydarmen.adventofcode.y2024

import com.heydarmen.adventofcode.PointLong

class Day13(input: List<String>) {
    private val claws = input.filter { it.isNotEmpty() }.chunked(3).map { l ->
        val a = l[0].removePrefix("Button A: ").split(", ")
            .map { p -> p.split("\\D+".toRegex()).filter { it.isNotEmpty() }.map { b -> b.toInt() } }.flatten()

        val b = l[1].removePrefix("Button B: ").split(", ")
            .map { p -> p.split("\\D+".toRegex()).filter { it.isNotEmpty() }.map { b -> b.toInt() } }.flatten()

        val p = l[2].removePrefix("Prize: ").split(", ")
            .map { p -> p.split("\\D+".toRegex()).filter { it.isNotEmpty() }.map { b -> b.toInt() } }.flatten()

        Claw(
            a = a.first().toLong() to a.last().toLong(),
            b = b.first().toLong() to b.last().toLong(),
            p = p.first().toLong() to p.last().toLong()
        )

    }

    fun solvePart1(): Long {
        return claws.sumOf {
            val r = findMinimalScore(it.p.first, it.p.second, it.a.first, it.a.second, it.b.first, it.b.second)
            if (r != null) r.first * 3 + r.second else 0
        }
    }

    fun solvePart2(): Long {
        return claws.map {
            Claw(
                a = it.a,
                b = it.b,
                p = it.p.first + 10000000000000 to it.p.second + 10000000000000,
            )
        }.sumOf {
            val r = findMinimalScore(it.p.first, it.p.second, it.a.first, it.a.second, it.b.first, it.b.second)
            if (r != null) r.first * 3 + r.second else 0
        }
    }

    private fun findMinimalScore(px: Long, py: Long, ax: Long, ay: Long, bx: Long, by: Long): Pair<Long, Long>? {
        var minScore = Long.MAX_VALUE
        var result: Pair<Long, Long>? = null

        val nMin = 0L
        val nMax = px / ax + 1

        for (n in nMin..nMax) {
            val remainderM = (px - ax * n) % bx
            if (remainderM != 0L) continue

            val m = (px - ax * n) / bx
            if (m < 0) continue

            val forecastedScore = 3 * n + m
            if (forecastedScore >= minScore) continue

            if (ay * n + by * m == py) {
                val score = 3 * n + m
                if (score < minScore) {
                    minScore = score
                    result = n to m
                }
            }
        }

        return result
    }

    private data class Claw(
        val a: PointLong,
        val b: PointLong,
        val p: PointLong
    )
}
