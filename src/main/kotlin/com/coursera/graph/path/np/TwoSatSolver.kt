package com.coursera.graph.path.np

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.Vertex
import com.coursera.graph.scc.StronglyConnectedComponentsEvaluator
import java.util.stream.Collectors

class TwoSatSolver(val input: List<Pair<Int, Int>>) {
    val vertexMapping = HashMap<Int, Int>()
    val vertexLookup = HashMap<Int, Int>()
    val graph = Graph()

    private fun createGraph(): Graph {
        var counter = 1
        for ((from, to) in input) {
            val fromId = vertexMapping.computeIfAbsent(from, { counter++ })
            val toId = vertexMapping.computeIfAbsent(to, { counter++ })
            val negatedFromId = vertexMapping.computeIfAbsent(-from, { counter++ })
            val negatedToId = vertexMapping.computeIfAbsent(-to, { counter++ })

            vertexLookup.putIfAbsent(fromId, from)
            vertexLookup.putIfAbsent(negatedFromId, -from)
            vertexLookup.putIfAbsent(toId, to)
            vertexLookup.putIfAbsent(negatedToId, -to)

            graph.addEdge(Edge(Vertex(negatedFromId), Vertex(toId)))
            graph.addEdge(Edge(Vertex(negatedToId), Vertex(fromId)))
        }

        return graph
    }

    public fun solve(): Boolean {
        createGraph()
        val scc = StronglyConnectedComponentsEvaluator(graph).evaluate()
//        val vertexes = input.stream()
//                .flatMap { listOf(it.first, it.second).stream() }
//                .filter { it > 0}
//                .collect(Collectors.toSet())

        for (component in scc) {
            for (vertex in component) {
                val id = vertexLookup[vertex.id]!!
                val negatedId = vertexMapping[-id] ?: continue
                if (component.contains(Vertex(negatedId))) {
                    return false
                }
            }
        }

        return true
    }
}