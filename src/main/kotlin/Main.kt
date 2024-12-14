import com.heydarmen.adventofcode.println
import com.heydarmen.adventofcode.readInput
import com.heydarmen.adventofcode.runMeasure
import com.heydarmen.adventofcode.y2024.Day14

fun main() {
    val input = readInput().lines()

//    runMeasure {
//        Day14(input)
//            .solvePart1()
//            .println()
//    }

    runMeasure {
        Day14((input))
            .solvePart2()
            .println()
    }
}
