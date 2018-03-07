package com.coursera.graph.clustering

import com.coursera.graph.InputType
import com.coursera.graph.readGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class ClusterEvaluatorTest {
    companion object {
        private val FILE_NAME = "src/test/resources/clustering.txt"
        private val SMALL_TEST_CASE_1 = "src/test/resources/clustering_small.txt"
    }

    @Test
    fun testClustering() {
        val graph = readGraph(File(FILE_NAME), " ", InputType.EDGE_LIST_WITH_WEIGHTS, false)
        val clusterEvaluator = ClusterEvaluator(graph)
        clusterEvaluator.evaluate(4)

        val maxSpace = clusterEvaluator.getMaxSpace()
        assertEquals(106, maxSpace)
    }

    @Test
    fun testClustering_Small1() {
        val graph = readGraph(File(SMALL_TEST_CASE_1), " ", InputType.EDGE_LIST_WITH_WEIGHTS, false)
        val clusterEvaluator = ClusterEvaluator(graph)

        val maxSpace = clusterEvaluator.getMaxSpace()
        assertEquals(100, maxSpace)
    }

}