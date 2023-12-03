data class N(val n: String, val coord: Pair<Int, Int>) {
    override fun equals(other: Any?): Boolean {
        if (other is N) {
            return other.n == this.n && other.coord == this.coord
        }

        return false
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + coord.hashCode()
        return result
    }
}

fun findAdjacentNumbers(n: List<N>, coordinates: Pair<Int, Int>): MutableList<N> {
    val result = mutableListOf<N>()

    val topLeft = n.filter {
        (it.coord.second == coordinates.second - 1) && coordinates.first - 1 in (it.coord.first..(it.n.length + it.coord.first - 1))
    }

    val topRight = n.filter {
        (it.coord.second == coordinates.second - 1) && coordinates.first + 1 in (it.coord.first..(it.n.length + it.coord.first - 1))
    }

    val bottomLeft = n.filter {
        (it.coord.second == coordinates.second + 1) && coordinates.first - 1 in (it.coord.first..(it.n.length + it.coord.first - 1))
    }
// 16-2 15-1
    val bottomRight = n.filter {
        (it.coord.second == coordinates.second + 1) && coordinates.first + 1 in (it.coord.first..(it.n.length + it.coord.first - 1))
    }

    val left = n.filter {
        (it.coord.second == coordinates.second) && coordinates.first - 1 in (it.coord.first..(it.n.length + it.coord.first - 1))
    }

    val right = n.filter {
        (it.coord.second == coordinates.second) && coordinates.first + 1 in (it.coord.first..(it.n.length + it.coord.first - 1))
    }

    val top = n.filter {
        (it.coord.second == coordinates.second - 1) && coordinates.first in (it.coord.first..(it.n.length + it.coord.first - 1))
    }

    val bottom = n.filter {
        (it.coord.second == coordinates.second + 1) && coordinates.first in (it.coord.first..(it.n.length + it.coord.first - 1))
    }

    result.addAll(topLeft)
    result.addAll(topRight)
    result.addAll(bottomLeft)
    result.addAll(bottomRight)
    result.addAll(left)
    result.addAll(right)
    result.addAll(top)
    result.addAll(bottom)

    return result
}

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = mutableListOf<N>()
        val stars = mutableListOf<Pair<Int, Int>>()

        input.forEachIndexed { lineNumber, line ->
            var n = ""
            var nStart = 0

            for (i in line.indices) {
                val c = line[i]

                if (c.isDigit()) {
                    if (n == "") nStart = i
                    n = "$n$c"
                    continue
                }

                if (c == '*') {
                    stars.add(i to lineNumber)
                    if (n != "") numbers.add(N(n, nStart to lineNumber))
                    n=""
                    continue
                }

                if (c == '.') {
                    if (n != "") numbers.add(N(n, nStart to lineNumber))
                    n = ""
                }


            }

            if (n != "") numbers.add(N(n, nStart to lineNumber))
        }


        val adjacent = mutableListOf<N>()

        stars.forEach {
            val result = findAdjacentNumbers(numbers, it).distinct()
            adjacent.addAll(result)
        }

        return adjacent.sumOf {
            it.n.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    part1(readInput(3, 1)).println()
}
