package com.coursera.trees

import java.text.MessageFormat.format

class DisjointSet<T> {

    private val values = HashMap<T, Node<T>>()
    private var setCount = 0

    private data class Node<T>(val value: T) {
        var rank = 1
        var ancestor = this
    }

    public fun add(value: T): Boolean {
        if (!values.contains(value)) {
            values.put(value, Node(value))
            ++setCount
            return true
        }

        return false
    }

    public fun merge(fst: T, snd: T) {
        var fstNode = values[fst]
        var sndNode = values[snd]
        if (fstNode == null || sndNode == null) {
            throw IllegalArgumentException(format("cannot merge not existing node: fst={0}, snd={1}", fst, snd))
        }

        fstNode = find(fstNode)
        sndNode = find(sndNode)

        when {
            fstNode.rank < sndNode.rank -> fstNode.ancestor = sndNode
            fstNode.rank > sndNode.rank -> sndNode.ancestor = fstNode
            else -> {
                sndNode.ancestor = fstNode
                fstNode.rank++
            }
        }
        --setCount
    }

    public fun find(value: T): T? {
        val node = values[value] ?: return null
        return find(node).value
    }

    private fun find(node: Node<T>): Node<T> {
        if (node.ancestor != node) {
            node.ancestor = find(node.ancestor)
        }

        return node.ancestor
    }

    public fun getCount(): Int {
        return setCount
    }
}