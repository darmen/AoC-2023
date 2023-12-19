package y2023.d19.p1

import println
import readText
import runMeasure

fun solve() {
    val input = readText()

    val workflows = input.split("\n\n")
        .first()
        .split("\n")
        .associate { w ->
            val key = w
                .dropLast(1)
                .split("{")
                .first()

            val value = w
                .dropLast(1)
                .split("{")
                .last()
                .split(",")

            key to value
        }

    val partRatings = input.split("\n\n")
        .last()
        .split("\n")
        .map {
            it
                .drop(1)
                .dropLast(1)
                .split(",")
                .associate {
                    val parts = it.split("=")
                    parts.first() to parts.last().toInt()
                }
        }

    var res = 0L

    pr@ for (pr in partRatings) {
        var state = "in"

        wh@ do {
            val conditions = workflows[state]!!
            conditions@ for (c in conditions) {
                if (!c.contains(":")) {
                    if (c == "A") {
                        res += pr.values.sum()
                        continue@pr
                    }

                    if (c == "R") {
                        continue@pr
                    }

                    state = c
                    break@conditions
                }

                val expression = c.split(":").first()
                val newWorkflowName = c.split(":").last()

                val argument = expression.split("<", ">").first()
                val value = expression.split("<", ">").last().toInt()

                if (expression.contains(">")) {
                    if (pr[argument]!! > value) {
                        state = newWorkflowName

                        if (state == "A") {
                            res += pr.values.sum()
                            continue@pr
                        }

                        if (state == "R") {
                            continue@pr
                        }

                        break@conditions
                    }
                }

                if (expression.contains("<")) {
                    if (pr[argument]!! < value) {
                        state = newWorkflowName

                        if (state == "A") {
                            res += pr.values.sum()
                            continue@pr
                        }

                        if (state == "R") {
                            continue@pr
                        }

                        break@conditions
                    }
                }
            }
        } while (true)
    }


    res.println()
}

fun main() {
    runMeasure { solve() }
}
