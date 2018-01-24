package com.coursera.graph.mincut

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.InputType.ADJACENCY_LIST
import com.coursera.graph.Vertex
import com.coursera.graph.readGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class KargerMinCutKtTest {

    companion object {
        private val FILE_NAME = "src/test/resources/kargerMinCut.txt"
        private val KARGER_MIN_CUT = KargerMinCut()
    }

    @Test
    fun testMinCutSquareGraph() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2)))
        graph.addEdge(Edge(Vertex(2), Vertex(3)))
        graph.addEdge(Edge(Vertex(3), Vertex(4)))
        graph.addEdge(Edge(Vertex(4), Vertex(1)))
        graph.addEdge(Edge(Vertex(1), Vertex(4)))
        graph.addEdge(Edge(Vertex(2), Vertex(1)))
        graph.addEdge(Edge(Vertex(3), Vertex(2)))
        graph.addEdge(Edge(Vertex(4), Vertex(3)))


        val minCut = KARGER_MIN_CUT.minCut(graph, 200)

        assertEquals(2, calculateCutScore(minCut))
    }

    @Test
    fun testMinCutAlmostSquareGraph() {
        val graph = Graph()

        graph.addEdge(Edge(Vertex(2), Vertex(3)))
        graph.addEdge(Edge(Vertex(3), Vertex(4)))
        graph.addEdge(Edge(Vertex(4), Vertex(1)))
        graph.addEdge(Edge(Vertex(1), Vertex(4)))
        graph.addEdge(Edge(Vertex(3), Vertex(2)))
        graph.addEdge(Edge(Vertex(4), Vertex(3)))


        val minCut = KARGER_MIN_CUT.minCut(graph, 200)

        assertEquals(1, calculateCutScore(minCut))
    }

    @Test
    fun testMinCutSquareWithDiagonalsGraph() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2)))
        graph.addEdge(Edge(Vertex(2), Vertex(3)))
        graph.addEdge(Edge(Vertex(3), Vertex(4)))
        graph.addEdge(Edge(Vertex(4), Vertex(1)))
        graph.addEdge(Edge(Vertex(1), Vertex(4)))
        graph.addEdge(Edge(Vertex(2), Vertex(1)))
        graph.addEdge(Edge(Vertex(3), Vertex(2)))
        graph.addEdge(Edge(Vertex(4), Vertex(3)))
        graph.addEdge(Edge(Vertex(1), Vertex(3)))
        graph.addEdge(Edge(Vertex(3), Vertex(1)))
        graph.addEdge(Edge(Vertex(2), Vertex(4)))
        graph.addEdge(Edge(Vertex(4), Vertex(2)))


        val minCut = KARGER_MIN_CUT.minCut(graph, 200)

        assertEquals(3, calculateCutScore(minCut))
    }

    @Test
    fun testMinCut() {
        val graph = readGraph(File(FILE_NAME), "\t", ADJACENCY_LIST)

        val minCut = KARGER_MIN_CUT.minCut(graph, 2000)

        assertEquals(17, calculateCutScore(minCut))
    }
}