package com.coursera.graph

import com.coursera.graph.InputType.ADJACENCY_LIST
import com.coursera.graph.InputType.EDGE_LIST
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.StringUtils
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

public enum class InputType {
    ADJACENCY_LIST,
    EDGE_LIST;
}

public fun readGraph(file: File, delimiter: String, inputType: InputType): Graph {
    if (!file.exists()) {
        throw IllegalArgumentException("file not exists: $file")
    }

    return when (inputType) {
        ADJACENCY_LIST -> readGraphFromAdjacencyList(file, delimiter)
        EDGE_LIST -> readFromEdgeList(file, delimiter)
    }
}

private fun readGraphFromAdjacencyList(file: File, delimiter: String): Graph {
    val graph = Graph()

    for (line in BufferedReader(FileReader(file)).lines()) {
        val tokens = line.split(delimiter).stream().filter(StringUtils::isNumeric).mapToInt { Integer.parseInt(it) }.toArray()

        if (ArrayUtils.isEmpty(tokens)) {
            throw IllegalStateException("there should be at least vertex id on the line")
        }

        val vertex = Vertex(tokens[0])

        for (i in 1 until tokens.size) graph.addEdge(Edge(vertex, Vertex(tokens[i])))
    }

    return graph
}

private fun readFromEdgeList(file: File, delimiter: String): Graph {
    val graph = Graph()

    for (line in BufferedReader(FileReader(file)).lines()) {
        val tokens = line.split(delimiter).stream().filter(StringUtils::isNumeric).mapToInt { Integer.parseInt(it) }.toArray()

        if (tokens.size != 2) {
            throw IllegalStateException("there should be at only two vertices for each edge: $line")
        }

        val vertexFrom = Vertex(tokens[0])
        val vertexTo = Vertex(tokens[1])

        graph.addEdge(Edge(vertexFrom, vertexTo))
    }

    return graph
}