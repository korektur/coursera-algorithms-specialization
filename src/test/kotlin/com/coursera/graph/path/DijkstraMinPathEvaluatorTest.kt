package com.coursera.graph.path

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.InputType
import com.coursera.graph.Vertex
import com.coursera.graph.readGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class DijkstraMinPathEvaluatorTest {

    companion object {
        private val FILE_NAME = "src/test/resources/Dijkstra.txt"
    }

    @Test
    fun testEvaluateSimplePathGraph() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2), 1))
        graph.addEdge(Edge(Vertex(2), Vertex(3), 2))
        graph.addEdge(Edge(Vertex(3), Vertex(4), 3))
        graph.addEdge(Edge(Vertex(4), Vertex(1), 4))

        val result = DijkstraMinPathEvaluator(graph).evaluate(Vertex(1))

        val expected = mapOf(
                Vertex(1) to 0L,
                Vertex(2) to 1L,
                Vertex(3) to 3L,
                Vertex(4) to 6L)

        assertEquals(expected, result)
    }


    @Test
    fun testEvaluateSimpleGraph() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2), 1))
        graph.addEdge(Edge(Vertex(2), Vertex(3), 2))
        graph.addEdge(Edge(Vertex(3), Vertex(4), 3))
        graph.addEdge(Edge(Vertex(4), Vertex(1), 4))
        graph.addEdge(Edge(Vertex(1), Vertex(3), 1))

        val result = DijkstraMinPathEvaluator(graph).evaluate(Vertex(1))

        val expected = mapOf(
                Vertex(1) to 0L,
                Vertex(2) to 1L,
                Vertex(3) to 1L,
                Vertex(4) to 4L)

        assertEquals(expected, result)
    }


    @Test
    fun testEvaluateGraphWithUnreachableVertex() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2), 1))
        graph.addEdge(Edge(Vertex(2), Vertex(3), 2))
        graph.addEdge(Edge(Vertex(4), Vertex(1), 4))
        graph.addEdge(Edge(Vertex(1), Vertex(3), 1))

        val result = DijkstraMinPathEvaluator(graph).evaluate(Vertex(1))

        val expected = mapOf(
                Vertex(1) to 0L,
                Vertex(2) to 1L,
                Vertex(3) to 1L)

        assertEquals(expected, result)
    }

    @Test
    fun testEvaluateGraphTriangle() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2), 4))
        graph.addEdge(Edge(Vertex(2), Vertex(3), 2))
        graph.addEdge(Edge(Vertex(1), Vertex(3), 1))
        graph.addEdge(Edge(Vertex(3), Vertex(2), 1))


        val result = DijkstraMinPathEvaluator(graph).evaluate(Vertex(1))

        val expected = mapOf(
                Vertex(1) to 0L,
                Vertex(2) to 2L,
                Vertex(3) to 1L)

        assertEquals(expected, result)
    }

    @Test
    fun testEvaluateGraphTriangeAndVertexInTheEdgeMiddle() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2), 1))
        graph.addEdge(Edge(Vertex(2), Vertex(4), 3))
        graph.addEdge(Edge(Vertex(3), Vertex(4), 2))
        graph.addEdge(Edge(Vertex(1), Vertex(3), 1))

        val result = DijkstraMinPathEvaluator(graph).evaluate(Vertex(1))

        val expected = mapOf(
                Vertex(1) to 0L,
                Vertex(2) to 1L,
                Vertex(3) to 1L,
                Vertex(4) to 3L)

        assertEquals(expected, result)
    }

    @Test
    fun testEvaluateGraphTricky() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2), 5))
        graph.addEdge(Edge(Vertex(2), Vertex(4), 5))
        graph.addEdge(Edge(Vertex(1), Vertex(3), 8))
        graph.addEdge(Edge(Vertex(3), Vertex(4), 1))

        val result = DijkstraMinPathEvaluator(graph).evaluate(Vertex(1))

        val expected = mapOf(
                Vertex(1) to 0L,
                Vertex(2) to 5L,
                Vertex(3) to 8L,
                Vertex(4) to 9L)

        assertEquals(expected, result)
    }

    @Test
    fun testEvaluateBigGraph() {
        val graph = readGraph(File(FILE_NAME), "\t", InputType.ADJACENCY_LIST_WITH_WEIGHTS)

        val result = DijkstraMinPathEvaluator(graph).evaluate(Vertex(1))
        val verticies = intArrayOf(7, 37, 59, 82, 99, 115, 133, 165, 188, 197)

        verticies.forEach { println(result[Vertex(it)]) }
    }
}