package y2019

import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath
import edu.uci.ics.jung.graph.DirectedSparseGraph
import edu.uci.ics.jung.graph.util.EdgeType
import println
import readInput
import runMeasure

class Day06(val input: List<String>) {
    private val graph = DirectedSparseGraph<String, String>()

    init {
        for (s in input) {
            val a = s.split(")").first()
            val b = s.split(")").last()

            graph.addVertex(a)
            graph.addVertex(b)

            graph.addEdge("$b -> $a", b, a, EdgeType.DIRECTED)
        }
    }

    fun solve1(): Int {
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
        val you = graph.getSuccessors("YOU").first()
        val san = graph.getSuccessors("SAN").first()
        val usp = UnweightedShortestPath(graph)

        return graph.vertices
            .mapNotNull { v ->
                val distanceYou = usp.getDistance(you, v)
                val distanceSan = usp.getDistance(san, v)

                if (distanceYou == null || distanceSan == null) {
                    return@mapNotNull null
                }

                distanceYou.toInt() + distanceSan.toInt()
            }
            .min()
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
