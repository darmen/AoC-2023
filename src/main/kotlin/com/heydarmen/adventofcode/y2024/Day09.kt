package com.heydarmen.adventofcode.y2024

class Day09(input: List<String>) {
    private val diskMap = input.first()

    private val files = mutableListOf<Block>()
    private val gaps = mutableListOf<Block>()

    init {
        var pos = 0

        diskMap.map { it.digitToInt() }.forEachIndexed { index, len ->
            (if (index % 2 == 0) files else gaps).add(Block(pos = pos, len = len, id = index / 2))
            pos += len
        }
    }

    fun solvePart1(): Long {
        val gaps = gaps.flatMap { s -> (0..<s.len).map { i -> Block(pos = s.pos + i, len = 1, id = s.id) } }
        val files = files.flatMap { f -> (0..<f.len).map { i -> Block(pos = f.pos + i, len = 1, id = f.id) } }

        return calculate(gaps.toMutableList(), files.toMutableList())
    }

    fun solvePart2(): Long {
        return calculate(gaps, files)
    }

    private fun calculate(gaps: MutableList<Block>, files: MutableList<Block>): Long {
        for (i in files.indices.reversed()) {
            val file = files[i]

            for (j in gaps.indices) {
                val gap = gaps[j]

                if (gap.pos >= file.pos) break

                if (gap.len >= file.len) {
                    files[i] = Block(
                        pos = gap.pos,
                        len = file.len,
                        id = file.id
                    )

                    gaps[j] = Block(
                        pos = gap.pos + file.len,
                        len = gap.len - file.len,
                        id = gap.id
                    )

                    break
                }
            }
        }

        return files.sumOf { f -> (0..<f.len).sumOf { i -> f.id.toLong() * (f.pos + i) } }
    }

    private data class Block(val pos: Int, val len: Int, val id: Int)
}
