package y2023.d19.p2

import println
import readInput
import runMeasure
import kotlin.math.max
import kotlin.math.min

sealed interface Condition
class Rule(val param: Char, val op: Char, val value: Long, val target: String) : Condition

class Fallback(val target: String) : Condition

fun solve() {
    val input = readInput()
        .takeWhile { it != "" }
        .associate { l ->
            val (name, rest) = l
                .dropLast(1)
                .split("{")

            val rules = rest.split(",").toMutableList()
            val fallback = Fallback(rules.removeLast())

            val q = rules
                .map { r ->
                    val p = r.split(':')

                    Rule(
                        param = p.first().first(),
                        op = p.first()[1],
                        value = p.first().drop(2).takeWhile { it != ':' }.toLong(),
                        target = p.last()
                    )
                }

            name to (q to fallback)
        }

    fun calculate(rng: Map<Char, LongRange>, name: String): Long {
        var ranges = rng.toMutableMap()

        if (name == "R") {
            return 0
        }

        if (name == "A") {
            return ranges.values.fold(1) { acc, range ->
                acc * (range.last - range.first + 1)
            }
        }

        val (rules, fallback) = input[name]!!

        var total = 0L

        var broke = false
        for (rule in rules) {
            val l = ranges[rule.param]!!.first
            val h = ranges[rule.param]!!.last

            val truePart = if (rule.op == '<') {
                l..min(h, rule.value - 1)
            } else {
                max(l, rule.value + 1)..h
            }

            val falsePart = if (rule.op == '<') {
                max(l, rule.value)..h
            } else {
                l..min(h, rule.value)
            }

            if (truePart.first <= truePart.last) {
                val copy = ranges.toMutableMap()
                copy[rule.param] = truePart
                total += calculate(copy, rule.target)
            }

            if (falsePart.first <= falsePart.last) {
                ranges = ranges.toMutableMap()
                ranges[rule.param] = falsePart
            } else {
                broke = true
                break
            }
        }

        if (!broke) {
            total += calculate(ranges, fallback.target)
        }

        return total
    }

    calculate("xmas".associate { it to 1L..4000L }, "in")
        .println()
}

fun main() {
    runMeasure { solve() }
}
