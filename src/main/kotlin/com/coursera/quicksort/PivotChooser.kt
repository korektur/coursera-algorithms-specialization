package com.coursera.quicksort

import com.coursera.quicksort.PivotChooseAlgorithm.*

enum class PivotChooseAlgorithm {
    /**
     * always take the first element in array as pivot
     */
    FIRST,
    LAST,
    MEDIAN_OF_THREE
}

/**
 * chooses pivot for given array, using specified algorithm
 * @param arr input array
 * @param l left boundary (inclusive)
 * @param r right boundary (exclusive)
 * @param pivotChooseAlgorithm algorithm to choose pivot
 * @see PivotChooseAlgorithm
 */
public fun choosePivot(arr: IntArray, l: Int, r: Int, pivotChooseAlgorithm: PivotChooseAlgorithm) : Int {
    when (pivotChooseAlgorithm) {
        FIRST -> return l
        LAST -> return r - 1
        MEDIAN_OF_THREE -> {
            val middleIndex = ((r - l - 1) / 2) + l
            if ((arr[l] <= arr[middleIndex] && arr[middleIndex] <= arr[r - 1]) ||
                    (arr[r - 1] <= arr[middleIndex] && arr[middleIndex] <= arr[l])) {
                return middleIndex
            }

            if ((arr[middleIndex] <= arr[l] && arr[l] <= arr[r - 1]) ||
                    (arr[r - 1] <= arr[l] && arr[l] <= arr[middleIndex])) {
                return l
            }

            return r - 1
        }
        else -> throw IllegalArgumentException("unknown algorithm type: " + pivotChooseAlgorithm)
    }
}