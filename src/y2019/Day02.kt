package y2019

import println
import readInput

class Day02(val input: List<String>) {
    private val program = input.first().split(",").map { it.toInt() }.mapIndexed { idx, it ->
        return@mapIndexed when (idx) {
            1 -> 12
            2 -> 2
            else -> it
        }
    }.toTypedArray()

    private val desiredOutput = 19690720

    fun solve1(): Any {
        return runProgram(program.clone())
    }

    fun solve2(): Any {
        for (noun in 0..99) {
            for (verb in 0..99) {
                val programClone = program.clone()
                programClone[1] = noun
                programClone[2] = verb

                val result = runProgram(programClone)

                if (result == desiredOutput) {
                    return 100 * noun + verb
                }
            }
        }

        throw IllegalStateException("No solution")
    }

    private fun runProgram(program: Array<Int>): Int {
        var opCodePosition = 0

        while (program[opCodePosition] != OpCode.HALT.code) {
            val opCode = OpCode.fromCode(program[opCodePosition])

            val operands = program[program[opCodePosition + 1]] to program[program[opCodePosition + 2]]

            val resultAddress = program[opCodePosition + 3]

            val result = if (opCode == OpCode.ADD) {
                operands.first + operands.second
            } else {
                operands.first * operands.second
            }

            program[resultAddress] = result

            opCodePosition += 4
        }

        return program[0]

    }

    private enum class OpCode(val code: Int) {
        ADD(1), MUL(2), HALT(99);

        companion object {
            fun fromCode(code: Int): OpCode {
                return when (code) {
                    ADD.code -> ADD
                    MUL.code -> MUL
                    HALT.code -> HALT
                    else -> {
                        throw IllegalArgumentException("Incorrect opcode")
                    }
                }
            }
        }
    }
}

fun main() {
    val day = Day02(readInput())

    day.solve1().println()
    day.solve2().println()
}