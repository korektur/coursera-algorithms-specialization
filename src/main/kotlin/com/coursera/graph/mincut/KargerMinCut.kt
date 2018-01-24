package com.coursera.graph.mincut

import com.coursera.graph.Graph
import com.google.common.annotations.VisibleForTesting
import java.util.Random

/**
 * Karger Graph min cut randomized algorithm implementation
 * @see <a href="https://en.wikipedia.org/wiki/Karger%27s_algorithm">Karger's algorithm</a>
 */
public class KargerMinCut {

    companion object {
        private val RANDOM = Random()
    }

    /**
     * Runs mincut algorithm n times
     * @param graph graph to search mincut
     * @param numberOfRuns number of runs to perform
     * @return graph consisting of two resulting nodes, representing mincut
     */
    fun minCut(graph: Graph, numberOfRuns: Int): Graph {
        var bestCut: Graph? = null
        var bestCutScore = Int.MAX_VALUE
        for (i in 1..numberOfRuns) {
            val result = minCutRun(graph)
            val cutScore = calculateCutScore(result)

            if (bestCut == null || cutScore < bestCutScore) {
                bestCutScore = cutScore
                bestCut = result
            }
        }

        return bestCut!!
    }


    private fun minCutRun(initialGraph: Graph): Graph {
        val graph = Graph(initialGraph)
        while (graph.numberOfVertices() > 2) {
            graph.edgeContraction(RANDOM.nextInt(graph.numberOfEdges()))
        }

        return graph
    }
}

@VisibleForTesting
fun calculateCutScore(graph: Graph): Int {
    if (graph.numberOfVertices() > 2) {
        throw IllegalArgumentException("cut must contain only 2 vertices: " + graph.edgeList)
    }

    return graph.adjacencyList.entries.iterator().next().value.size
}