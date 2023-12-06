package y2022

import println
import readInput

enum class Shape(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3)
}

enum class Result {
    WIN, LOSS, DRAW
}

val shapeWin = mapOf(
    Shape.ROCK to Shape.SCISSORS,
    Shape.PAPER to Shape.ROCK,
    Shape.SCISSORS to Shape.PAPER,
)

fun main() {
    fun part1(input: List<String>): Int {

        val letterToShape = mapOf(
            "A" to Shape.ROCK,
            "B" to Shape.PAPER,
            "C" to Shape.SCISSORS,
            "X" to Shape.ROCK,
            "Y" to Shape.PAPER,
            "Z" to Shape.SCISSORS,
        )

        var result = 0

        for (i in input.indices) {
            val it = input[i]

            val opponentsShape = letterToShape[it.split(" ").first()]!!
            val myShape = letterToShape[it.split(" ").last()]!!

            result += myShape.score

            // win
            if (shapeWin[myShape] == opponentsShape) {
                result += 6
                continue
            }

            // loss
            if (shapeWin[opponentsShape] == myShape) {
                result += 0
                continue
            }

            // draw
            if (opponentsShape == myShape) {
                result += 3
                continue
            }
        }

        return result;
    }

    fun part2(input: List<String>): Int {
        val letterToShape = mapOf(
            "A" to Shape.ROCK,
            "B" to Shape.PAPER,
            "C" to Shape.SCISSORS,
        )

        val letterToResult = mapOf(
            "X" to Result.LOSS,
            "Y" to Result.DRAW,
            "Z" to Result.WIN,
        )

        var result = 0

        for (i in input.indices) {
            val it = input[i]

            val opponentsShape = letterToShape[it.split(" ").first()]!!
            val expectedResult = letterToResult[it.split(" ").last()]!!

            if (expectedResult == Result.DRAW) {
                result += opponentsShape.score + 3
                continue
            }

            if (expectedResult == Result.WIN) {
                val shape = shapeWin.filter {
                    it.value == opponentsShape
                }.keys.first()

                result += shape.score + 6
                continue
            }


            if (expectedResult == Result.LOSS) {
                val shape = shapeWin[opponentsShape]!!
                result += shape.score + 0
                continue
            }
        }

        return result;
    }


    part1(readInput(2, 1)).println()
    part2(readInput(2, 2)).println()
}
