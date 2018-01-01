package com.coursera.quicksort

import com.coursera.quicksort.PivotChooseAlgorithm.MEDIAN_OF_THREE
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PivotChooserKtTest {

    @Test
    internal fun testMedianOfThreeEven() {
        val arr = intArrayOf(8, 2, 4, 5, 7, 1)

        assertEquals(4, arr[choosePivot(arr, 0, arr.size, MEDIAN_OF_THREE)])
    }

    @Test
    internal fun testMedianOfThreeOdd() {
        val arr = intArrayOf(8, 2, 5, 7, 1)

        assertEquals(5, arr[choosePivot(arr, 0, arr.size, MEDIAN_OF_THREE)])
    }
}