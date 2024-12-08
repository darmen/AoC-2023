import com.heydarmen.adventofcode.println
import com.heydarmen.adventofcode.readInput
import com.heydarmen.adventofcode.runMeasure
import com.heydarmen.adventofcode.y2024.Day06

fun main() {
    val input = readInput().lines()

    runMeasure {
        Day06(input)
            .solvePart1()
            .println()
    }
}
