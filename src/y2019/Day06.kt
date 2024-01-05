package y2019

import edu.uci.ics.jung.graph.DirectedSparseGraph
import edu.uci.ics.jung.graph.util.EdgeType
import println
import readInput
import runMeasure

class Day06(val input: List<String>) {
    private val graph = DirectedSparseGraph<String, String>()

    fun solve1(): Int {
        for (s in input) {
            val a = s.split(")").first()
            val b = s.split(")").last()

            graph.addVertex(a)
            graph.addVertex(b)

            graph.addEdge("$b -> $a", b, a, EdgeType.DIRECTED)
        }

        var r = 0
        for (vertex in graph.vertices.filterNot { it == "COM" }) {
            var v = vertex
            while (v != "COM") {
                r++
                v = graph.getSuccessors(v).first()
            }
        }

        return r
    }

    fun solve2(): Int {
        TODO("Not implemented yet")
    }
}

fun main() {
    val day = Day06(readInput())

    runMeasure {
        day.solve1().println()
    }

    runMeasure {
        day.solve2().println()
    }
}
