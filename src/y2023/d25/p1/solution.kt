package y2023.d25.p1

import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer
import edu.uci.ics.jung.graph.Graph
import edu.uci.ics.jung.graph.UndirectedSparseGraph
import println
import readInput
import runMeasure


/**
 * @link Girvan-Newman algorithm
 * @link https://networkx.guide/algorithms/community-detection/girvan-newman/#how-does-it-work
 */
private fun solve() {
    val input = readInput()

    val graph: Graph<String, Edge> = UndirectedSparseGraph()

    for (i in input.indices) {
        val (first, rest) = input[i].split(": ")
        val cs = rest.split(" ")

        graph.addVertex(first)

        for (c in cs) {
            graph.addVertex(c)
            graph.addEdge(Edge(first to c), first, c)
        }
    }


    val cluster = EdgeBetweennessClusterer<String, Edge>(3)

    cluster
        .apply(graph)?.let {
            it.toList().fold(1) { acc, strings ->
                acc * strings.size
            }
        }.println()
}

fun main() {
    runMeasure { solve() }
}

private data class Edge(
    val ns: Pair<String, String>
)