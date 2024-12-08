package com.heydarmen.adventofcode.y2024

import com.heydarmen.adventofcode.println
import com.heydarmen.adventofcode.runMeasure
import org.junit.jupiter.api.Test

class Day06Test {
    private val input = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent().lines()

    @Test
    fun `runs with test input`() {
        runMeasure {
            Day06(input)
                .solvePart1()
                .println()
        }
    }
}
