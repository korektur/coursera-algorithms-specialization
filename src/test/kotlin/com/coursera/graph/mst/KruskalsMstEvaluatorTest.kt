package com.coursera.graph.mst

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.InputType
import com.coursera.graph.Vertex
import com.coursera.graph.readGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

internal class KruskalsMstEvaluatorTest {
    companion object {
        private val FILE_NAME = "src/test/resources/PrimColoring.txt"
    }

    @Test
    fun testMstSmallGraph() {

        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2), 1))
        graph.addEdge(Edge(Vertex(2), Vertex(3), 4))
        graph.addEdge(Edge(Vertex(3), Vertex(4), 3))
        graph.addEdge(Edge(Vertex(1), Vertex(4), 2))
        val mstEvaluator = KruskalsMstEvaluator(graph)

        val res = mstEvaluator.evaluate()
        val count = res.stream().mapToLong { it.weight }.sum()
        assertEquals(6, count)
    }

    @Test
    fun testMst() {

        val graph = readGraph(File(FILE_NAME), " ", InputType.EDGE_LIST_WITH_WEIGHTS, false)

        val mstEvaluator = KruskalsMstEvaluator(graph)
        val primsMst = PrimsMstEvaluator(graph)
        val expected = primsMst.evaluate()
        val res = mstEvaluator.evaluate()

        val set = HashSet<Edge>(res)
        for (edge in expected) {
            assertTrue(set.contains(edge) || set.contains(Edge(edge.to, edge.from, edge.weight)))
        }

        val count = res.stream().mapToLong { it.weight }.sum()
        assertEquals(-3612829, count)
    }
}