import com.heydarmen.adventofcode.println
import com.heydarmen.adventofcode.readInput
import com.heydarmen.adventofcode.runMeasure
import com.heydarmen.adventofcode.y2024.Day09

fun main() {
    val input = readInput().lines()

    runMeasure {
        Day09(input)
            .solvePart1()
            .println()
    }

    runMeasure {
        Day09((input))
            .solvePart2()
            .println()
    }
}
