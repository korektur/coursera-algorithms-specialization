package com.coursera.graph.path

import com.coursera.graph.Edge
import com.coursera.graph.Graph
import com.coursera.graph.InputType
import com.coursera.graph.Vertex
import com.coursera.graph.path.FloydMinPathEvaluator.Companion.INF
import com.coursera.graph.readGraph
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.File


class FloydMinPathEvaluatorTest {

    @Test
    fun testSimple() {
        val graph = Graph()
        graph.addEdge(Edge(Vertex(1), Vertex(2), 1))
        graph.addEdge(Edge(Vertex(2), Vertex(4), 1))
        graph.addEdge(Edge(Vertex(2), Vertex(3), 4))
        graph.addEdge(Edge(Vertex(1), Vertex(3), 6))
        graph.addEdge(Edge(Vertex(4), Vertex(3), 1))

        val result = FloydMinPathEvaluator(graph).evaluate()

        val expected = arrayOf(longArrayOf(0L, 1L, 3L, 2L),
                longArrayOf(INF, 0L, 2L, 1L),
                longArrayOf(INF, INF, 0L, INF),
                longArrayOf(INF, INF, 1L, 0L))

        assertArrayEquals(expected, result)
    }


    @ParameterizedTest
    @ValueSource(strings = [
        "src/test/resources/allminpath_g1.txt",
        "src/test/resources/allminpath_g2.txt",
        "src/test/resources/allminpath_g3.txt"])
    fun testMinPath(file: String) {
        val graph = readGraph(File(file), " ", InputType.EDGE_LIST_WITH_WEIGHTS, true)
        val result = FloydMinPathEvaluator(graph).evaluate()
        println(result.orEmpty().flatMap { it.asIterable() }.min())
    }
}
