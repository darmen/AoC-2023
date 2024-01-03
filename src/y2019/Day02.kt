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

    fun solve1(): Any {
        var opCodePosition = 0

        while (this.program[opCodePosition] != OpCode.HALT.code) {
            val opCode = OpCode.fromCode(this.program[opCodePosition])
            val operands = this.program[this.program[opCodePosition + 1]] to this.program[this.program[opCodePosition + 2]]
            val resultStorage = this.program[opCodePosition + 3]

            val result = if (opCode == OpCode.ADD) {
                operands.first + operands.second
            } else {
                operands.first * operands.second
            }

            this.program[resultStorage] = result

            opCodePosition += 4
        }

        return this.program[0]
    }

    fun solve2(): Any {
        TODO("Not implemented yet")
    }

    enum class OpCode(val code: Int) {
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