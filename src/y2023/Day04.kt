package y2023

import println
import readInput
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        input.forEachIndexed { i, s ->
            val groups = s.split(":")[1].split(" | ")
            val first = groups[0].split(" ").filter { it != "" }.map { it.toInt() }
            val second = groups[1].split(" ").filter { it != "" }.map { it.toInt() }

            val size = second.intersect(first).toList().size.toDouble() - 1

            sum += 2.toDouble().pow(size).toInt()
        }

        return sum
    }

    fun getSize(s: String): Int {
        val groups = s.split(":")[1].split(" | ")
        val first = groups[0].split(" ").filter { it != "" }.map { it.toInt() }
        val second = groups[1].split(" ").filter { it != "" }.map { it.toInt() }

        return second.intersect(first).toList().size
    }

    fun getValuesToAdd(cardNo: Int, cardWins2: Map<Int, MutableList<Int>>): MutableList<Int> {
        val valuesToAdd = cardWins2[cardNo]!!
        val result = mutableListOf<Int>()
        result.addAll(valuesToAdd)

        valuesToAdd.forEach{
            result += getValuesToAdd(it, cardWins2)
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        val sizes = mutableListOf<Int>()
        val cards = mutableListOf<Int>()
        val cardWins = mutableMapOf<Int, MutableList<Int>>()
        val cardWins2 = mutableMapOf<Int, MutableList<Int>>()

        input.forEachIndexed { i, it ->
            sizes.add(
                getSize(it)
            )
        }

        sizes.forEachIndexed { i, s ->
            cardWins[i+1] = (i+2..i+1+s).toMutableList()
            cardWins2[i+1] = (i+2..i+1+s).toMutableList()
        }


        cardWins.forEach {
            it.value.forEach { cardNo ->
                if (it.key != cardNo) {
                    val valuesToAdd = getValuesToAdd(cardNo, cardWins2)
                    if (valuesToAdd.isNotEmpty()) cardWins2[it.key]!!.addAll(valuesToAdd)
                }
            }
        }

        var res = 0
        cardWins2.values.forEach {
            res += it.size
        }

        return res + sizes.size
    }

    part1(readInput(4, 1)).println()
    part2(readInput(4, 2)).println()
}
