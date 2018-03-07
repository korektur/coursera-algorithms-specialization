package com.coursera.graph.mst

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.trees.DisjointSet
import java.util.Comparator.comparing
import java.util.stream.Collectors

class KruskalsMstEvaluator(private val graph: Graph) : MstEvaluator {

    public override fun evaluate(): List<Edge> {
        val edges = graph.edgeList.stream().sorted(comparing(Edge::weight)).collect(Collectors.toList())
        val result = ArrayList<Edge>()
        val set = DisjointSet<Int>()

        var i = 0
        while(i < edges.size && result.size != graph.numberOfVertices() - 1) {
            val edge = edges[i++]
            val to = edge.to.id
            val from = edge.from.id
            set.add(to)
            set.add(from)
            if (set.find(to) != set.find(from)) {
                result.add(edge)
                set.merge(to, from)
            }

        }

        return result
    }
}