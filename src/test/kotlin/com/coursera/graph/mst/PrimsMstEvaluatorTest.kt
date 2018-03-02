package com.coursera.graph.mst

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.InputType
import com.coursera.graph.Vertex
import com.coursera.graph.readGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class PrimsMstEvaluatorTest {
    companion object {
        private val FILE_NAME = "src/test/resources/PrimColoring.txt"
    }

    @Test
    internal fun testMstSmallGraph() {

        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2), 1))
        graph.addEdge(Edge(Vertex(2), Vertex(3), 3))
        graph.addEdge(Edge(Vertex(3), Vertex(4), 2))
        graph.addEdge(Edge(Vertex(1), Vertex(4), 1))
        val primsMstEvaluator = PrimsMstEvaluator(graph)

        val res = primsMstEvaluator.evaluate()
        val count = res.stream().mapToLong { it.weight }.sum()
        assertEquals(5, count)
    }

    @Test
    internal fun testMst() {

        val graph = readGraph(File(FILE_NAME), " ", InputType.EDGE_LIST_WITH_WEIGHTS, false)

        val primsMstEvaluator = PrimsMstEvaluator(graph)

        val res = primsMstEvaluator.evaluate()
        val count = res.stream().mapToLong { it.weight }.sum()
        assertEquals(-3612829, count)
    }
}