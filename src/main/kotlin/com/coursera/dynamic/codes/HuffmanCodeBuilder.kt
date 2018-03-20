package com.coursera.dynamic.codes

import com.google.common.base.MoreObjects
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class HuffmanCodeBuilder<T>(private val symbolWeights: Map<T, Long>) {

    private val comparator = Comparator.comparingLong<Vertex<T>> { it.weight }
    private val initialQueue: Queue<Vertex<T>> = ArrayDeque<Vertex<T>>()
    private val newVertexQueue: Queue<Vertex<T>> = PriorityQueue<Vertex<T>>(comparator)
    private var root: Vertex<T>? = null
    private val encodingMap: MutableMap<T, String> = HashMap()

    public data class Vertex<T>(public val symbols: Set<T>,
                                public val weight: Long,
                                public var parent: Vertex<T>?,
                                public val leftChild: Vertex<T>?,
                                public val rightChild: Vertex<T>?) {
        constructor(symbols: Set<T>, weight: Long) : this(symbols, weight, null, null, null)

        override fun toString(): String {
            return MoreObjects.toStringHelper(this)
                    .add("symbols", symbols)
                    .add("weight", weight)
                    .add("leftChild", leftChild)
                    .add("rightChild", rightChild)
                    .omitNullValues()
                    .toString()
        }
    }

    public fun build(): Map<T, String> {
        symbolWeights.entries.stream()
                .map { Vertex(setOf(it.key), it.value) }
                .sorted(comparator)
                .forEach { initialQueue.add(it) }

        while (initialQueue.size > 0 || newVertexQueue.size > 1) {
            val fst = getNextVertex()
            val snd = getNextVertex()
            val newParent = createParent(fst, snd)
            newVertexQueue.add(newParent)
        }

        root = newVertexQueue.poll()
        buildEncodingMap(root!!, "")
        return encodingMap
    }

    private fun buildEncodingMap(root: Vertex<T>, code: String) {
        if (root.symbols.size == 1) {
            encodingMap[root.symbols.elementAt(0)] = code
            return
        }

        buildEncodingMap(root.leftChild!!, code + "0")
        buildEncodingMap(root.rightChild!!, code + "1")
    }

    private fun createParent(fst: Vertex<T>, snd: Vertex<T>): Vertex<T> {
        val symbols = HashSet<T>(fst.symbols.size + snd.symbols.size)
        symbols.addAll(fst.symbols)
        symbols.addAll(snd.symbols)

        val parent = Vertex(symbols, fst.weight + snd.weight, null, fst, snd)
        fst.parent = parent
        snd.parent = parent
        return parent
    }

    private fun getNextVertex(): Vertex<T> {
        if (initialQueue.isEmpty() && newVertexQueue.isEmpty()) {
            throw IllegalStateException("no vertexes to extract")
        }

        if (initialQueue.isEmpty()) {
            return newVertexQueue.poll()
        }

        if (newVertexQueue.isEmpty()) {
            return initialQueue.poll()
        }

        if (comparator.compare(initialQueue.peek(), newVertexQueue.peek()) <= 0) {
            return initialQueue.poll()
        }

        return newVertexQueue.poll()
    }
}