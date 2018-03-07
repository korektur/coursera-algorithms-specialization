package com.coursera.graph.mst

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.Vertex
import java.util.*
import kotlin.collections.ArrayList

class PrimsMstEvaluator(private val graph: Graph): MstEvaluator {

    private val visited = BitSet(graph.numberOfVertices() + 1)

    public override fun evaluate(): List<Edge> {
        val source = graph.edgeList[0].from
        val priorityQueue = PriorityQueue<Edge>(Comparator.comparing(Edge::weight))
        val edgesInQueueByHead = HashMap<Vertex, Edge>()
        priorityQueue.add(Edge(Vertex(0), source, 0L))

        val res = ArrayList<Edge>(graph.numberOfVertices())

        while (priorityQueue.size > 0) {
            val edge = priorityQueue.poll()
            if (visited[edge.to.id]) continue

            visited[edge.to.id] = true
            res.add(edge)

            val newEdges = graph.adjacencyList[edge.to]
                    .orEmpty()
                    .filter { !visited[it.to.id] }
            for (newEdge in newEdges) {
                if (edgesInQueueByHead.containsKey(newEdge.to)) {
                    val curEdge = edgesInQueueByHead[newEdge.to]!!
                    if (curEdge.weight > newEdge.weight) {
                        priorityQueue.remove(curEdge)
                        priorityQueue.add(newEdge)
                        edgesInQueueByHead.put(newEdge.to, newEdge)
                    }
                } else {
                    priorityQueue.add(newEdge)
                    edgesInQueueByHead.put(newEdge.to, newEdge)
                }

            }
        }
        return res.subList(1, res.size)
    }
}