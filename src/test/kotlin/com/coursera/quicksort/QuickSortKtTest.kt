package com.coursera.quicksort

import org.apache.commons.lang3.mutable.MutableLong
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.io.BufferedReader
import java.io.FileReader
import java.util.*
import java.util.stream.IntStream

internal class QuickSortKtTest {

    @ParameterizedTest
    @EnumSource(value = PivotChooseAlgorithm::class, names = ["FIRST", "LAST", "MEDIAN_OF_THREE"])
    fun testQuickSort(algo: PivotChooseAlgorithm) {
        val array = IntStream.generate { Companion.RANDOM.nextInt() }
                .limit(10000)
                .distinct()
                .toArray()

        val expected = Arrays.copyOf(array, array.size)
        Arrays.sort(expected)

        quickSort(array, MutableLong(0), algo)

        assertArrayEquals(expected, array)
    }

    @ParameterizedTest
    @EnumSource(value = PivotChooseAlgorithm::class, names = ["FIRST", "LAST", "MEDIAN_OF_THREE"])
    fun testQuickSortWithTestInput(algo: PivotChooseAlgorithm) {
        val array = BufferedReader(FileReader(FILE_NAME)).lines().mapToInt { it -> Integer.parseInt(it) }.toArray()

        val expected = Arrays.copyOf(array, array.size)
        Arrays.sort(expected)

        val comparisonCounter = MutableLong(0)
        quickSort(array, comparisonCounter, algo)

        assertArrayEquals(expected, array)
        println(comparisonCounter)
    }

    companion object {
        private val FILE_NAME = "src/test/resources/QuickSort.txt"
        private val RANDOM = Random()
    }
}