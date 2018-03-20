package com.coursera.dynamic

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

internal class KnapsackProblemSolverKtTest {

    @Test
    fun testSolveSimple() {
        val items = listOf(Item(3, 1),
                Item(4, 6),
                Item(5, 4),
                Item(8, 7),
                Item(9, 6))
        assertEquals(13, solve(13, items))
    }

    @ParameterizedTest
    @ValueSource(strings = ["src/test/resources/knapsackSmall.txt", "src/test/resources/knapsackBig.txt"])
    fun testSolveBigInput(file: String) {
        val input = Files.lines(Paths.get(file)).collect(Collectors.toList())
        val (capacity, itemsCount) = input[0].split(" ").map { it.toInt() }
        val items = ArrayList<Item>(itemsCount)
        for (i in 1 until input.size) {
            val (value, weight) = input[i].split(" ")
            items.add(Item(weight.toInt(), value.toLong()))
        }

        println(solve(capacity, items))
    }
}