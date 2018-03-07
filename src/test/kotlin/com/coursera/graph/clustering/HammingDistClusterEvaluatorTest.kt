package com.coursera.graph.clustering

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors.toList

internal class HammingDistClusterEvaluatorTest {
    companion object {
        private val FILE_NAME = "src/test/resources/clustering_big.txt"
    }

    @Test
    fun evaluate() {
        val nodes = Files.lines(Paths.get(FILE_NAME)).skip(1).map { convert(it) }.collect(toList())
        val hammingDistClusterEvaluator = HammingDistClusterEvaluator(nodes)
        val result = hammingDistClusterEvaluator.evaluate(3)

        assertEquals(6118, result)
    }

    private fun convert(str: String): BitSet {
        val split = str.split(" ")
        val bitSet = BitSet(split.size)
        for (i in 0 until split.size) {
            bitSet[i] = split[i] == "1"
        }

        return bitSet
    }
}