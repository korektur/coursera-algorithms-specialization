package com.coursera.inversions

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.stream.IntStream

internal class InversionsCounterKtTest {

    companion object {
        private val FILE_NAME = "src/test/resources/IntegerArray.txt"
    }

    @Test
    fun testCountInversions() {
        val file = File(Companion.FILE_NAME)
        val input = BufferedReader(FileReader(file)).lines().mapToInt { it -> Integer.parseInt(it) }.toArray()

        assertEquals(2407905288, countInversions(input))
    }

    @Test
    fun testMergeAndCountWithoutInversion() {
        val left: IntArray = intArrayOf(1, 2, 3, 4, 5)
        val right: IntArray = intArrayOf(6, 7, 8, 9, 10)
        val resultArr = IntArray(10)
        val result = mergeAndCount(left, right, resultArr)

        assertEquals(0, result)
        assertArrayEquals(resultArr, IntStream.range(1, 11).toArray())
    }

    @Test
    fun testMergeAndCountWithSplitInversions() {
        val left: IntArray = intArrayOf(1, 3, 5)
        val right: IntArray = intArrayOf(2, 4, 6)
        val resultArr = IntArray(6)
        val result = mergeAndCount(left, right, resultArr)

        assertEquals(3, result)
        assertArrayEquals(resultArr, IntStream.range(1, 7).toArray())
    }
}