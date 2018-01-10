package com.coursera.mincut

import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.StringUtils.isNumeric
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

data class Vertex(val id: Int)

data class Edge(var from: Vertex, var to: Vertex)

public class Graph {

    val edgeList: MutableList<Edge>
    val adjacencyList: MutableMap<Vertex, MutableList<Edge>>

    fun numberOfVertices() = adjacencyList.keys.size

    fun numberOfEdges() = edgeList.size

    constructor() {
        this.edgeList = ArrayList()
        this.adjacencyList = HashMap()
    }

    constructor(other: Graph) {
        this.adjacencyList = HashMap()
        this.edgeList = ArrayList()
        other.edgeList.stream().map { it.copy() }.forEach { addEdge(it) }
    }

    fun addEdge(edge: Edge) {
        edgeList.add(edge)
        if (!adjacencyList.containsKey(edge.from)) {
            //linked list because we want to delete edges
            val edges = ArrayList<Edge>()
            edges.add(edge)
            adjacencyList.put(edge.from, edges)
        } else {
            adjacencyList[edge.from]!!.add(edge)
        }
    }

    fun edgeContraction(index: Int) {
        val edge = edgeList[index]

        val vertexToReplace  = edge.to

        val vertex = edge.from
        if (vertex == vertexToReplace ) {
            adjacencyList[vertex]!!.remove(edge)
            return
        }

        adjacencyList[vertex]!!.addAll(adjacencyList[vertexToReplace ].orEmpty())
        adjacencyList.remove(vertexToReplace )


        for (curEdge in edgeList) {
            if (curEdge.from == vertexToReplace) {
                curEdge.from = vertex
            }

            if (curEdge.to == vertexToReplace) {
                curEdge.to = vertex
            }
        }

        adjacencyList.put(vertex,
                adjacencyList[vertex].orEmpty()
                        .stream()
                        .filter { it.from != it.to }
                        .collect(Collectors.toList()))

        edgeList.removeIf { it.from == it.to }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Graph

        if (edgeList != other.edgeList) return false
        if (adjacencyList != other.adjacencyList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = edgeList.hashCode()
        result = 31 * result + adjacencyList.hashCode()
        return result
    }

    override fun toString(): String {
        return "Graph(edgeList=$edgeList, adjacencyList=$adjacencyList)"
    }


}

fun readGraph(file: File, delimiter: String): Graph {
    if (!file.exists()) {
        throw IllegalArgumentException("file not exists: $file")
    }

    val graph = Graph()

    for (line in BufferedReader(FileReader(file)).lines()) {
        val tokens = line.split(delimiter).stream().filter(::isNumeric).mapToInt { Integer.parseInt(it) }.toArray()

        if (ArrayUtils.isEmpty(tokens)) {
            throw IllegalStateException("there should be at least vertex id on the line")
        }

        val vertex = Vertex(tokens[0])

        for (i in 1 until tokens.size) graph.addEdge(Edge(vertex, Vertex(tokens[i])))
    }

    return graph
}