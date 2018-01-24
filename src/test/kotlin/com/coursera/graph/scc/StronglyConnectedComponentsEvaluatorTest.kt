package com.coursera.graph.scc

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.InputType.EDGE_LIST
import com.coursera.graph.Vertex
import com.coursera.graph.readGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class StronglyConnectedComponentsEvaluatorTest {

    companion object {
        private val FILE_NAME = "src/test/resources/SCC.txt"
    }

    @Test
    fun testSCCSimple() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2)))
        graph.addEdge(Edge(Vertex(2), Vertex(3)))
        graph.addEdge(Edge(Vertex(3), Vertex(4)))
        graph.addEdge(Edge(Vertex(4), Vertex(1)))

        val result = StronglyConnectedComponentsEvaluator(graph).evaluate()

        assertEquals(listOf(setOf(Vertex(1), Vertex(2), Vertex(3), Vertex(4))), result)
    }

    @Test
    fun testSCCWithFourComponents() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2)))
        graph.addEdge(Edge(Vertex(2), Vertex(3)))
        graph.addEdge(Edge(Vertex(3), Vertex(4)))
        graph.addEdge(Edge(Vertex(1), Vertex(4)))

        val result = StronglyConnectedComponentsEvaluator(graph).evaluate()

        assertEquals(setOf(setOf(Vertex(4)), setOf(Vertex(3)), setOf(Vertex(1)), setOf(Vertex(2))), result.toSet())
    }

    @Test
    fun testSCCBigDataSet() {
        val graph = readGraph(File(FILE_NAME), " ", EDGE_LIST)

        val result = StronglyConnectedComponentsEvaluator(graph).evaluate()

        result.sortedByDescending { it.size }
                .subList(0, 5)
                .forEach { println(it.size) }
    }
}