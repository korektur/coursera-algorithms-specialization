package com.coursera.trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.stream.IntStream

internal class DisjointSetTest {
    @Test
    fun testAdd() {
        val set = DisjointSet<Int>()
        val arr = IntStream.range(0, 10).toArray()
        arr.forEach { set.add(it) }

        arr.forEach { assertEquals(it, set.find(it)) }

        assertFalse(set.add(1))
    }

    @Test
    fun testMerge() {
        val set = DisjointSet<Int>()
        val arr = intArrayOf(1, 2, 3, 4)
        arr.forEach { set.add(it) }

        set.merge(1, 2)
        set.merge(3, 4)

        assertEquals(set.find(1), set.find(2))
        assertEquals(set.find(3), set.find(4))
        assertNotEquals(set.find(1), set.find(3))
        assertNotEquals(set.find(1), set.find(4))
    }
}