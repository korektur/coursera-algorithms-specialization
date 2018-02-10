package com.coursera.hashtables

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class TwoSumProblemResolverTest {

    companion object {
        private val FILE_NAME = "src/test/resources/2sumProblem.txt"
    }

    @Test
    fun testOnSimpleArray() {
        val input = LongArray(10, { it.toLong() })
        val resolver = TwoSumProblemResolver(input)

        assertEquals(0, resolver.hasSupplements(100L..1000L))
        assertEquals(0, resolver.hasSupplements(-100L..0L))

        assertEquals(10, resolver.hasSupplements(1..10L))
    }

    @Test
    fun testOnBigFile() {

        val arr = Files.lines(Paths.get(FILE_NAME))
                .mapToLong { it.toLong() }
                .toArray()
        val resolver = TwoSumProblemResolver(arr)

        val counter = resolver.hasSupplements(-10000..10000L)

        println(counter)
    }
}