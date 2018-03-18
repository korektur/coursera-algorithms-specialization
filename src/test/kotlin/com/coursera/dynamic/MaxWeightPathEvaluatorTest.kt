package com.coursera.dynamic

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths


internal class MaxWeightPathEvaluatorTest {
    companion object {
        private val FILE_NAME = "src/test/resources/mwis.txt"
    }

    @Test
    fun testOnFile() {
        val weights = Files.lines(Paths.get(FILE_NAME)).skip(1).mapToLong { it.toLong() }.toArray()
        val evaluator = MaxWeightPathEvaluator(weights)
        val maxWeight = evaluator.evaluate()
        println(maxWeight)
        val path = evaluator.getPath()
        println(path)
        println(path.intersect(listOf(1, 2, 3, 4, 17, 117, 517, 997)))
    }
}