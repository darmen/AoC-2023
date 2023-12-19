package y2023.d19.p2

import Point
import println
import readInput
import runMeasure
import java.util.Stack

sealed interface Condition

class NormalCondition(val param: Char, val op: Char, val value: Int, val target: String) : Condition {
    fun evaluate(): Int {
        return if (op == '<') {
            value - 1
        } else {
            4000 - value
        }
    }
}

class Pointer(val target: String) : Condition

fun solve() {
    val input = readInput()
        .takeWhile { it != "" }
        .associate {
            val w = it
                .dropLast(1)
                .split("{")

            val q = w.last()
                .split(",")
                .map { c ->
                    if (c.contains(':')) {
                        val p = c.split(':')
                        NormalCondition(
                            param = p.first().first(),
                            op = p.first()[1],
                            value = p.first().drop(2).takeWhile { it != ':' }.toInt(),
                            target = p.last()
                        )
                    } else {
                        Pointer(c)
                    }
                }

            w.first() to q
        }

    var res = 0L

    val visited = mutableSetOf<Condition>()
    val stack = Stack<String>()
    stack.push("in")

    wh@ while (stack.isNotEmpty()) {
        val level = stack.peek()
        fo@ for (c in input[level]!!) {
            if (c is NormalCondition && c.target == "A") {
                // inc
                continue@fo
            }

            if (c is Pointer && c.target == "A") {
                // inc
                continue@fo
            }


            if (c is NormalCondition && c.target == "R") {
                continue@fo
            }

            if (c is Pointer && c.target == "R") {
                continue@fo
            }

            if (c in visited) {
                continue
            }

            visited.add(c)

            stack.push(
                when (c) {
                    is NormalCondition -> c.target
                    is Pointer -> c.target
                }
            )

            break@fo
        }
    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}
