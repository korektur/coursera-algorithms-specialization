package com.coursera.graph.clustering

import com.coursera.trees.DisjointSet
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HammingDistClusterEvaluator(val nodes: List<BitSet>) {
    private val clusters = DisjointSet<Int>()
    private val map = HashMap<BitSet, MutableList<Int>>()

    public fun evaluate(maxDist: Int): Int {
        for (i in 0 until nodes.size) {
            clusters.add(i)
            map.computeIfAbsent(nodes[i], { ArrayList() }).add(i)
        }

        for (i in 0 until maxDist) {
            for (j in 0 until nodes.size) {
                findAllNeighboursAndConnect(nodes[j], j, i, 0)
            }
        }

        return clusters.getCount()
    }
    
    private fun findAllNeighboursAndConnect(nodeDesc: BitSet, sourceId: Int, dist: Int, index: Int) {
        if (dist == 0) {
            val otherNodes = map[nodeDesc].orEmpty()
            @Suppress("LoopToCallChain")
            for (node in otherNodes) {
                if (clusters.find(node) != clusters.find(sourceId)) {
                    clusters.merge(node, sourceId)
                }
            }
            return
        }

        if (index == nodeDesc.size()) {
            return
        }

        if (nodeDesc.size() - index > dist) {
            findAllNeighboursAndConnect(nodeDesc, sourceId, dist, index + 1)
        }

        nodeDesc.flip(index)
        findAllNeighboursAndConnect(nodeDesc, sourceId, dist - 1, index + 1)
        nodeDesc.flip(index)
    }
}