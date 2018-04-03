package com.coursera.graph.path

import com.coursera.graph.Graph
import kotlin.math.min

class FloydMinPathEvaluator(val graph: Graph) {

    companion object {
        const val INF = 10000000000L
    }

    val n = graph.numberOfVertices()

    public fun evaluate(): Array<LongArray>? {
        val d = Array(n, { LongArray(n, { INF }) })

        for (i in 0 until n) {
            d[i][i] = 0
        }

        for (edge in graph.edgeList) {
            d[edge.from.id - 1][edge.to.id - 1] = min(d[edge.from.id - 1][edge.to.id - 1], edge.weight)
        }

        for (i in 0 until n) {
            for (u in 0 until n) {
                for (v in 0 until n) {
                    d[u][v] = min(d[u][v], d[u][i] + d[i][v])
                }
            }
        }

        for (i in 0 until n) {
            if (d[i][i] < 0) {
                return null
            }
        }

        return d
    }
}