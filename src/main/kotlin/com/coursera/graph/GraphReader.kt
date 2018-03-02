package com.coursera.graph

import com.coursera.graph.InputType.ADJACENCY_LIST
import com.coursera.graph.InputType.ADJACENCY_LIST_WITH_WEIGHTS
import com.coursera.graph.InputType.EDGE_LIST
import com.coursera.graph.InputType.EDGE_LIST_WITH_WEIGHTS
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.StringUtils
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

public enum class InputType {
    ADJACENCY_LIST,
    ADJACENCY_LIST_WITH_WEIGHTS,
    EDGE_LIST,
    EDGE_LIST_WITH_WEIGHTS;
}

public fun readGraph(file: File, delimiter: String, inputType: InputType, direted: Boolean): Graph {
    if (!file.exists()) {
        throw IllegalArgumentException("file not exists: $file")
    }

    return when (inputType) {
        ADJACENCY_LIST -> readGraphFromAdjacencyList(file, delimiter)
        EDGE_LIST -> readFromEdgeList(file, delimiter)
        ADJACENCY_LIST_WITH_WEIGHTS -> readGraphFromAdjacencyListWithWeights(file, delimiter)
        EDGE_LIST_WITH_WEIGHTS -> readFromEdgeListWithWeights(file, delimiter, direted)
    }
}

private fun readGraphFromAdjacencyList(file: File, delimiter: String): Graph {
    val graph = Graph()

    for (line in BufferedReader(FileReader(file)).lines()) {
        val tokens = line.split(delimiter).stream().filter(StringUtils::isNumeric).mapToInt { it.toInt() }.toArray()

        if (ArrayUtils.isEmpty(tokens)) {
            throw IllegalStateException("there should be at least vertex id on the line")
        }

        val vertex = Vertex(tokens[0])

        for (i in 1 until tokens.size) graph.addEdge(Edge(vertex, Vertex(tokens[i])))
    }

    return graph
}


private fun readGraphFromAdjacencyListWithWeights(file: File, delimiter: String): Graph {
    val graph = Graph()

    for (line in BufferedReader(FileReader(file)).lines()) {
        val tokens = line.split(delimiter)

        if (tokens.isEmpty()) {
            throw IllegalStateException("there should be at least vertex id on the line")
        }

        val vertex = Vertex(tokens[0].toInt())

        for (i in 1 until tokens.size) {
            if (tokens[i].isEmpty()) continue
            val (to, weight) = tokens[i].split(",")
            graph.addEdge(Edge(vertex, Vertex(to.toInt()), weight.toLong()))
        }
    }

    return graph
}


private fun readFromEdgeList(file: File, delimiter: String): Graph {
    val graph = Graph()

    for (line in BufferedReader(FileReader(file)).lines()) {
        val tokens = line.split(delimiter).stream().filter(StringUtils::isNumeric).mapToInt { it.toInt() }.toArray()

        if (tokens.size != 2) {
            throw IllegalStateException("there should be at only two vertices for each edge: $line")
        }

        val vertexFrom = Vertex(tokens[0])
        val vertexTo = Vertex(tokens[1])

        graph.addEdge(Edge(vertexFrom, vertexTo))
    }

    return graph
}

private fun readFromEdgeListWithWeights(file: File, delimiter: String, direted: Boolean): Graph {
    val graph = Graph()

    for (line in BufferedReader(FileReader(file)).lines().skip(1)) {
        val tokens = line.split(delimiter).stream().mapToInt { it.toInt() }.toArray()

        if (tokens.size != 3) {
            throw IllegalStateException("there should be at only two vertices for each edge: $line")
        }

        val vertexFrom = Vertex(tokens[0])
        val vertexTo = Vertex(tokens[1])
        val weight = tokens[2].toLong()
        graph.addEdge(Edge(vertexFrom, vertexTo, weight))
        if (!direted) {
            graph.addEdge(Edge(vertexTo, vertexFrom, weight))
        }
    }

    return graph
}