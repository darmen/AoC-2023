package y2024.d05

import println
import readInput
import runMeasure

private fun solve() {
    val input = readInput()
    val orderingRules = mutableListOf<String>()
    val updates = mutableListOf<String>()

    var wasBrake = false
    input.forEach { s ->
        if (s == "") {
            wasBrake = true
            return@forEach
        }

        if (wasBrake) {
            updates.add(s)
        } else {
            orderingRules.add(s)
        }
    }

    val orderMap = mutableMapOf<Int, MutableList<Int>>()
    orderingRules.forEach { rule ->
        val parts = rule.split("|").map { it.toInt() }
        if (orderMap.containsKey(parts[0])) {
            orderMap[parts[0]]?.add(parts[1])
        } else {
            orderMap[parts[0]] = mutableListOf(parts[1])
        }
    }

    val incorrectParts = mutableListOf<List<Int>>()

    updates.forEach { rule ->
        val parts = rule.split(",").map { it.toInt() }
        var correct = true

        parts.forEachIndexed { index, n ->
            val others = parts.subList(index + 1, parts.size)

            if (index == parts.size - 1) {
                return@forEachIndexed
            }

            if (!orderMap.containsKey(n)) {
                incorrectParts.add(parts)
                correct = false
            }

            if (orderMap.containsKey(n) && orderMap[n]?.containsAll(others) == false) {
                incorrectParts.add(parts)
                correct = false
            }

            if (!correct) return@forEach
        }
    }

    val newList = incorrectParts.map { parts ->
        parts.sortedWith { a, b ->
            when {
                orderMap.containsKey(a) && orderMap[a]?.contains(b) == true -> -1
                orderMap.containsKey(a) && orderMap[a]?.contains(b) == false -> 1
                !orderMap.containsKey(a) -> 1
                else -> a.compareTo(b)
            }
        }
    }

    newList.sumOf { parts -> parts[(parts.size - 1) / 2].toLong() }.println()
}

fun main() {
    runMeasure { solve() }
}
