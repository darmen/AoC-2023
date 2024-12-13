import com.heydarmen.adventofcode.println
import com.heydarmen.adventofcode.readInput
import com.heydarmen.adventofcode.runMeasure
import com.heydarmen.adventofcode.y2024.Day13

fun main() {
    val input = readInput().lines()

    runMeasure {
        Day13(input)
            .solvePart1()
            .println()
    }

    runMeasure {
        Day13((input))
            .solvePart2()
            .println()
    }
}
