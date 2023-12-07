package y2023.d7.p1

import readInput
import println

val strengthByCard = listOf(
    'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'
)

fun distill(s: String): Map<Char, Int> {
    val m = mutableMapOf<Char, Int>()

    for (i in s.indices) {
        val c = s[i]

        if (m.containsKey(c)) continue

        m[c] = s.count { it == c }
    }

    return m
}

fun mapToHand(m: Map<Char, Int>): Hand {
    if (m.size == 1) {
        return Hand.FIVE
    }

    if (m.size == 2) {
        val sortedValues = m.values.sortedDescending()

        if (sortedValues.first() == 4) {
            return Hand.FOUR
        }

        return Hand.FULL_HOUSE
    }

    if (m.size == 3) {
        val sortedValues = m.values.sortedDescending()

        if (sortedValues.first() == 3 && sortedValues[1] == 1 && sortedValues[2] == 1) {
            return Hand.THREE
        }

        if (sortedValues.first() == 2 && sortedValues[1] == 2) {
            return Hand.TWO_PAIR
        }
    }

    if (m.size == 4) {
        val sortedValues = m.values.sortedDescending()

        if (sortedValues.first() == 2) {
            return Hand.ONE_PAIR
        }
    }

    return Hand.HIGH_CARD
}

fun processEntry(s: String): Hand {
    val m = distill(s)

    return mapToHand(m)
}

data class Entry(val code: String, val hand: Hand, val bid: Long) : Comparable<Entry> {
    override fun compareTo(other: Entry): Int {
        val myPos = Hand.entries.indexOf(this.hand)
        val otherPos = Hand.entries.indexOf(other.hand)

        if (myPos == otherPos) {

            for (i in this.code.indices) {
                val myC = this.code[i]
                val otherC = other.code[i]

                val myPosC = strengthByCard.indexOf(myC)
                val otherPosC = strengthByCard.indexOf(otherC)

                if (myPosC == otherPosC) {
                    continue
                }

                if (myPosC < otherPosC) {
                    return 1
                }

                return -1
            }

            return 0
        }

        if (myPos < otherPos) {
            return 1
        }

        return -1
    }

}

enum class Hand {
    FIVE, FOUR, FULL_HOUSE, THREE, TWO_PAIR, ONE_PAIR, HIGH_CARD
}

fun main() {

    fun part1(input: List<String>): Long {
        var result = 0L

        val entries = mutableListOf<Entry>()
        for (i in input.indices) {
            val s = input[i]

            val code = s.split(" ").first()
            val bid = s.split(" ").last().toLong()

            entries.add(
                Entry(hand = processEntry(code), code = code, bid = bid)
            )
        }

        entries.sorted().forEachIndexed { index, entry ->
            result += entry.bid * (index + 1)
        }

        return result
    }

    part1(readInput(7, 1)).println()
}
