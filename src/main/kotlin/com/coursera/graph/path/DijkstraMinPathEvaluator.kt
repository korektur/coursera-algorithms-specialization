package com.coursera.graph.path

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.Vertex
import java.util.*
import java.util.Comparator.comparing
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class DijkstraMinPathEvaluator(private val graph: Graph) {
    companion object {
        val INF = Long.MAX_VALUE
    }

    private val dist = LongArray(graph.numberOfVertices() + 1, { INF })
    private val visited = BitSet(graph.numberOfVertices() + 1)

    public fun evaluate(source: Vertex): Map<Vertex, Long> {
        val priorityQueue = PriorityQueue<Edge>(comparing(Edge::weight))
        val edgesInQueueByHead = HashMap<Vertex, Edge>()

        priorityQueue.add(Edge(Vertex(0), source, 0))
        dist[0] = 0

        while (priorityQueue.size > 0) {
            val edge = priorityQueue.poll()
            if (visited[edge.to.id]) continue

            visited[edge.to.id] = true
            val newDist = calculateDistance(edge)
            if (dist[edge.to.id] > newDist) {
                val newEdges = graph.adjacencyList[edge.to]
                        .orEmpty()
                        .filter { !visited[it.to.id] }
                val edgesToAdd = ArrayList<Edge>()
                for (newEdge in newEdges) {
                    if (edgesInQueueByHead.containsKey(newEdge.to)) {
                        val previousEdge = edgesInQueueByHead[newEdge.to]
                        if (calculateDistance(previousEdge!!) > newDist + newEdge.weight) {
                            priorityQueue.remove(edge)
                            edgesToAdd.add(newEdge)
                        }
                    } else {
                        edgesToAdd.add(newEdge)
                    }
                }

                dist[edge.to.id] = newDist
                edgesToAdd.forEach {
                    priorityQueue.add(it)
                    edgesInQueueByHead[it.to] = it
                }
            }
        }

        val result = HashMap<Vertex, Long>()
        for (i in 1..graph.numberOfVertices()) {
            if (dist[i] == INF) continue
            result.put(Vertex(i), dist[i])
        }

        return result
    }

    private fun calculateDistance(edge: Edge): Long {
        if (dist[edge.from.id] == INF) return INF

        return dist[edge.from.id] + edge.weight
    }
}