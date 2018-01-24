package com.coursera.graph.scc

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.Vertex
import org.apache.commons.lang3.mutable.MutableInt
import java.util.*
import kotlin.collections.HashSet

class StronglyConnectedComponentsEvaluator(val graph: Graph) {

    public fun evaluate(): List<Set<Vertex>> {
        val reverseGraph = evaluateReverseGraph(graph)
        val t = MutableInt(1)
        val labels = HashMap<Int, Vertex>()
        val visited = BooleanArray(reverseGraph.numberOfVertices(), { false })

        @Suppress("LoopToCallChain")
        for (i in reverseGraph.numberOfVertices() downTo 1) {
            if (visited[i - 1]) continue
            dfs(reverseGraph, Vertex(i), visited, { labels.put(t.andIncrement, it) })
        }

        val components = ArrayList<Set<Vertex>>()
        Arrays.fill(visited, false)

        @Suppress("LoopToCallChain")
        for (i in reverseGraph.numberOfVertices() downTo 1) {
            val vertex = labels[i]!!
            if (visited[vertex.id - 1]) continue
            val component = HashSet<Vertex>()
            dfs(graph, vertex, visited, { component.add(it) })
            components.add(component)
        }

        return components
    }

    companion object {
        private fun evaluateReverseGraph(graph: Graph): Graph {
            val reversedGraph = Graph()
            graph.edgeList
                    .map { Edge(it.to, it.from) }
                    .forEach { reversedGraph.addEdge(it) }

            return reversedGraph
        }

        private fun dfs(graph: Graph, vertex: Vertex, visited: BooleanArray, onFinish: (Vertex) -> Unit) {
            visited[vertex.id - 1] = true

            for (it in graph.adjacencyList[vertex].orEmpty()) {
                if (!visited[it.to.id - 1]) {
                    visited[it.to.id - 1] = true
                    dfs(graph, it.to, visited, onFinish)
                }
            }

            onFinish(vertex)
        }
    }
}