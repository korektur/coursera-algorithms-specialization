package com.coursera.trees

import java.text.MessageFormat.format

class DisjointSet<T> {

    private val values = HashMap<T, Node<T>>()

    private data class Node<T>(val value: T) {
        var rank = 1
        var ancestor = this
    }

    public fun add(value: T) {
        if (values.contains(value)) {
            throw IllegalArgumentException("already contrains given key: " + value)
        }

        values.put(value, Node(value))
    }

    public fun merge(fst: T, snd: T) {
        val fstNode = values[fst]
        val sndNode = values[snd]
        if (fstNode == null || sndNode == null) {
            throw IllegalArgumentException(format("cannot merge not existing node: fst={0}, snd={1}", fst, snd))
        }

        if (fstNode.rank < sndNode.rank) {
            fstNode.ancestor = sndNode
        } else if (fstNode.rank > sndNode.rank) {
            sndNode.ancestor = fstNode
        } else {
            sndNode.ancestor = fstNode
            fstNode.rank++
        }
    }

    public fun find(value: T): T {
        val node = values[value] ?: throw IllegalArgumentException("value not found: " + value)
        return find(node).value
    }

    private fun find(node: Node<T>): Node<T> {
        if (node.ancestor != node) {
            node.ancestor = find(node.ancestor)
        }

        return node.ancestor
    }
}