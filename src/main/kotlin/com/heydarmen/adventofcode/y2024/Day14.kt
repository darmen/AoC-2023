package com.heydarmen.adventofcode.y2024

import com.heydarmen.adventofcode.PointLong

class Day14(input: List<String>) {
    private val robots = input.filter { it.isNotEmpty() }.map { s ->
        val parts = s.split(" ").map { p -> p.removePrefix("p=").removePrefix("v=").split(",").map { it.toLong() } }
        val o = parts.map { (it[0] to it[1]) }
        Robot(o.first(), o.last())
    }

//    private val boundaries = 11L to 7L
    private val boundaries = 101L to 103L

    private val center = boundaries.toList().map { (it - 1) / 2 }.zipWithNext().single()

    fun solvePart1(): Long {
        for (s in 1..100) {
            for (r in robots) {
                r.p = move(r.p.first, r.v.first, boundaries.first) to move(r.p.second, r.v.second, boundaries.second)
            }
        }

        var q1 = 0L
        var q2 = 0L
        var q3 = 0L
        var q4 = 0L

        for (r in robots) {
            q1 += if (r.p.first < center.first && r.p.second < center.second) 1 else 0
            q2 += if (r.p.first > center.first && r.p.second < center.second) 1 else 0
            q3 += if (r.p.first < center.first && r.p.second > center.second) 1 else 0
            q4 += if (r.p.first > center.first && r.p.second > center.second) 1 else 0
        }

        return q1 * q2 * q3 * q4
    }

    private fun printRobots() {
        for (i in 0..<boundaries.first) {
            for (j in 0..<boundaries.second) {
                print(if (robots.any { it.p == i to j }) '#' else ' ')
            }
            println()
        }

        println()
        println()
    }

    private fun getRobotsAsString(): String {
        var result = ""
        for (i in 0..<boundaries.first) {
            for (j in 0..<boundaries.second) {
                result += if (robots.any { it.p == i to j }) '#' else ' '
            }
            result += "\n"
        }

        return result
    }

    private fun move(init: Long, step: Long, max: Long): Long {
        val result = (init + step) % max
        return if (result < 0) result + max else result
    }

    fun solvePart2(): Long {
        var i = 0L
        val boundaryX = boundaries.first
        val boundaryY = boundaries.second
//        val fAtomic = AtomicLong(0)

        while (true) {
            i++
//            fAtomic.set(0)

            robots.parallelStream().forEach { r ->
                r.p = r.p.copy(
                    first = move(r.p.first, r.v.first, boundaryX),
                    second = move(r.p.second, r.v.second, boundaryY)
                )
//                if (r.p.second == 0L) fAtomic.incrementAndGet()
            }

            println(i)
            printRobots()
//            val f = fAtomic.get() // Safely get the value of f
        }
    }


    private data class Robot(var p: PointLong, var v: PointLong) {
        override fun equals(other: Any?): Boolean {
            if (other is Robot) {
                return p == other.p && v == other.v
            }

            return false
        }

        override fun hashCode(): Int {
            var result = p.hashCode()
            result = 31 * result + v.hashCode()
            return result
        }
    }
}
