fun main() {
    fun part1(input: List<String>): Int {
        val colorMap = mutableMapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14,
        )

        var sum = 0

        input.forEach { line ->
            val splitLine = line.split(": ")
            val gameId = splitLine[0].replace("Game ", "").toInt()

            var possible = true

            splitLine[1].split("; ").forEach {attempt ->
                attempt.split(", ").forEach {hand ->
                    val quantity = hand.split(" ")[0].toInt()
                    val color = hand.split(" ")[1]

                    if (quantity > colorMap[color]!!) {
                        possible = false
                    }
                }
            }

            if (possible) {
                sum += gameId
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        input.forEach { line ->
            val colorsQuantity = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
            )

            val splitLine = line.split(": ")
            val gameId = splitLine[0].replace("Game ", "").toInt()

            splitLine[1].split("; ").forEach {attempt ->
                attempt.split(", ").forEach {hand ->
                    val quantity = hand.split(" ")[0].toInt()
                    val color = hand.split(" ")[1]

                    if (quantity > colorsQuantity[color]!!) {
                        colorsQuantity[color] = quantity
                    }
                }
            }

            var power = 1

            colorsQuantity.values.forEach {
                power *= it
            }

            sum += power
        }

        return sum
    }

//    part1(readInput(2, 1)).println()
    part2(readInput(2, 2)).println()
}
