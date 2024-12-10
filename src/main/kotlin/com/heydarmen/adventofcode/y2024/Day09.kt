package com.heydarmen.adventofcode.y2024

class Day09(input: List<String>) {
    private val diskMap = input.first()
    private val hash = mutableListOf<Int>()
    private val blocks = mutableListOf<Block>()

    init {
        var isFile = true
        diskMap.forEachIndexed { index, c ->
            blocks.add(
                Block(
                    pos = index,
                    id = if (isFile) index / 2 else null,
                    len = c.digitToInt(),
                    isFile = isFile)
            )

            isFile = !isFile
        }
        diskMap.forEachIndexed { index, c ->
            if ((index + 1) % 2 == 0) {
                hash.addAll(List(c.digitToInt()) { -1 })
            } else {
                hash.addAll(List(c.digitToInt()) { index / 2 })
            }
        }
    }

    fun solvePart2(): Long {
        val files = blocks.filter { it.isFile }

        for (file in files.reversed()) {
            var gap: Block? = null

            for (block in blocks) {
                if (block.isFile) continue

                if (block.pos <= file.pos && block.len >= file.len) {
                    gap = block
                    break
                }
            }

             if (gap == null) {
                continue
            }

            blocks[blocks.indexOf(file)] = Block(-1, null, file.len, false)

            if (file.len < gap.len) {
                blocks.add(blocks.indexOf(gap)+1, Block(-1, null, gap.len - file.len, false))
            }

            blocks[blocks.indexOf(gap)] = file

        }

        val s = mutableListOf<Int>()

        blocks.forEach { b->
            s.addAll(List(b.len){ if (b.isFile) b.id!!.toInt() else -1 })
        }

        var res = 0L

        for (index in 0..s.lastIndex) {
            val c = s[index]

            if (c == -1) {
                continue
            }

            res += index.toLong() * c.toLong()
        }

        return res
    }

    fun solvePart1(): Long {
        outer@ for (i in 0..<hash.size) {
            if (i >= hash.size) break@outer

            val c = hash[i]

            if (c != -1) {
                continue@outer
            }

            var k = hash.size - 1
            while (hash[k] == -1) {
                k--
                hash.removeLast()
            }

            if (k > i) {
                hash[i] = hash[k]
                hash.removeLast()
            }
        }

        var res = 0L
        hash.forEachIndexed { index, c ->
            if (c == -1) {
                return@forEachIndexed
            }

            res += index * c
        }

        return res
    }

    private data class Block(val pos: Int, val id: Int? = null, val len: Int, val isFile: Boolean)

}
