package com.coursera.graph.clustering

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.Vertex
import com.coursera.trees.DisjointSet
import java.util.Comparator.comparing
import java.util.stream.Collectors

class ClusterEvaluator(val graph: Graph) {

    private var clusters: Collection<List<Vertex>>? = null
    private val usedEdges: MutableList<Edge> = ArrayList()
    private val set = DisjointSet<Int>()

    public fun evaluate(k: Int): Collection<List<Vertex>> {
        if (clusters != null) {
            return clusters!!
        }

        if (graph.numberOfVertices() < k) {
            throw IllegalArgumentException("k should be less then number of vertices")
        }

        val edges = graph.edgeList.stream().sorted(comparing(Edge::weight)).collect(Collectors.toList())

        for (j in 1..graph.numberOfVertices()) {
            set.add(j)
        }

        var i = 0
        while (i < edges.size && set.getCount() != k) {
            val edge = edges[i++]
            val to = edge.to.id
            val from = edge.from.id
            if (set.find(to) != set.find(from)) {
                usedEdges.add(edge)
                set.merge(to, from)
            }

        }

        val clusters = HashMap<Int, MutableList<Vertex>>()
        for (j in 1..graph.numberOfVertices()) {
            val root = set.find(j)!!
            clusters.computeIfAbsent(root, { ArrayList() })
            clusters[root]!!.add(Vertex(j))
        }

        this.clusters = clusters.values
        return this.clusters!!
    }

    public fun getMaxSpace(): Long? {
        return graph.edgeList.stream()
                .sorted(comparing(Edge::weight))
                .filter { set.find(it.from.id) != set.find(it.to.id) }
                .map { it.weight }
                .min(naturalOrder())
                .orElse(null)
    }
}