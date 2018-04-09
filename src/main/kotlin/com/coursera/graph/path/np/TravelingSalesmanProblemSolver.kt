package com.coursera.graph.path.np

import com.google.common.math.IntMath.pow
import java.lang.Long.min
import java.lang.Long.toString

class TravelingSalesmanProblemSolver(val graph: Array<DoubleArray>) {

    companion object {
        public val INF = Double.MAX_VALUE
        private var counter = 0L
    }

    private val n = graph.size
    private val maxMask = pow(2, n)
    private val d = Array(n, { DoubleArray(maxMask) })

    public suspend fun evaluate(): Double {
        for (i in 0 until n) {
            for (mask in 0 until maxMask) {
                d[i][mask] = INF
                if (mask == 0) {
                    d[i][mask] = 0.0
                }
            }
        }

        d[0][0] = 0.0
        val findCheapest = findCheapest(0, (1 shl n) - 1, 0)
        return findCheapest
    }

    private fun findCheapest(id: Int, mask: Int, depth: Int): Double {
        ++counter
        if (d[id][mask] != INF) {
            return d[id][mask]
        }

        if (mask == 1) {
            d[id][mask] = graph[id][0]
            return d[id][mask]
        }

        for (j in 1 until n) {
            if (j == id) {
                continue
            }

            val marker = 1 shl j // 2^j
            if (graph[id][j] != INF && mask.and(marker) == marker) {
                val findCheapest = findCheapest(j, mask.xor(marker), depth + 1)
                if (findCheapest != INF) {
                    val newDist = (findCheapest + graph[id][j]) % Int.MAX_VALUE
                    d[id][mask] = kotlin.math.min(d[id][mask], newDist)

                }
            }
        }

        return d[id][mask]
    }
}