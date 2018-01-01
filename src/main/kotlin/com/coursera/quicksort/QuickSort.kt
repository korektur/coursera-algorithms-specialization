package com.coursera.quicksort

import org.apache.commons.lang3.mutable.MutableLong


/**
 * sort an array using QuickSort algorithm.
 * @param arr array to sort
 * @param comparisonCounter counter for number of comparisons done during sorting
 * @param pivotChooseAlgorithm algorithm which will be used to choose pivot during sorting
 * @see PivotChooseAlgorithm
 */
public fun quickSort(arr: IntArray, comparisonCounter: MutableLong, pivotChooseAlgorithm: PivotChooseAlgorithm) {
    quickSort(arr, 0, arr.size, comparisonCounter, pivotChooseAlgorithm)
}

private fun quickSort(arr: IntArray,
                      l: Int,
                      r: Int,
                      comparisonCounter: MutableLong,
                      pivotChooseAlgorithm: PivotChooseAlgorithm) {
    if (l >= r) {
        return
    }

    comparisonCounter.add(r - l - 1)

    val pivotIndex = choosePivot(arr, l, r, pivotChooseAlgorithm)
    val pivot = arr[pivotIndex]
    arrayElementsSwap(arr, pivotIndex, l)

    var i = l + 1

    for (j in (l + 1)..(r - 1)) {
        if (arr[j] < pivot) {
            arrayElementsSwap(arr, i++, j)
        }
    }

    arrayElementsSwap(arr, l, i - 1)

    quickSort(arr, l, i - 1, comparisonCounter, pivotChooseAlgorithm)
    quickSort(arr, i, r, comparisonCounter, pivotChooseAlgorithm)
}

private fun arrayElementsSwap(arr: IntArray, i: Int, j: Int) {
    val tmp = arr[i]
    arr[i] = arr[j]
    arr[j] = tmp
}