package com.coursera.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import java.util.stream.IntStream

internal class MedianEvaluatorTest {

    companion object {
        private val FILE_NAME = "src/test/resources/Median.txt"

    }

    @ParameterizedTest
    @ValueSource(ints = [1, 10, 15, 100, 150, 1000, 10000])
    fun testMedianOnRange(size: Int) {
        val evaluator = MedianEvaluator<Int>(naturalOrder())
        val array = IntStream.range(1, size + 1).boxed().collect(Collectors.toList<Int>())
        array.shuffle()

        array.forEach { evaluator.add(it) }

        assertEquals(size / 2 + size % 2, evaluator.getMedian())
    }

    @Test
    fun testWithStreamFromFile() {
        val evaluator = MedianEvaluator<Int>(naturalOrder())
        var result = 0L
        val lines = Files.readAllLines(Paths.get(FILE_NAME)).map { it.toInt() }

        for (e in lines) {
            evaluator.add(e)
            result += evaluator.getMedian()!!
        }

        assertEquals(46831213, result)
    }
}