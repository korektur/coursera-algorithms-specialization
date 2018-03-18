package com.coursera.dynamic.codes

import org.apache.commons.lang3.mutable.MutableInt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class HuffmanCodeBuilderTest {
    companion object {
        private val FILE_NAME = "src/test/resources/huffman.txt"
    }

    @Test
    fun testSimpleCode() {
        val symbolWeights = mapOf('a' to 32L, 'b' to 25L, 'c' to 20L, 'd' to 18L, 'e' to 5L)
        val huffmanCodeBuilder = HuffmanCodeBuilder(symbolWeights)

        val encodingMap = huffmanCodeBuilder.build()

        val expected = mapOf('a' to "11", 'b' to "10", 'c' to "00", 'd' to "011", 'e' to "010")
        assertEquals(expected, encodingMap)
    }

    @Test
    fun testOnBigInput() {
        val id = MutableInt(0)
        val input = Files.lines(Paths.get(FILE_NAME))
                .skip(1)
                .mapToLong { it.toLong() }
                .toArray()
                .map { id.andIncrement to it }
                .toMap()
        val huffmanCodeBuilder = HuffmanCodeBuilder(input)

        val encodingMap = huffmanCodeBuilder.build()

        assertEquals(19, encodingMap.entries.maxBy { it.value.length }!!.value.length)
        assertEquals(9, encodingMap.entries.minBy { it.value.length }!!.value.length)
    }
}