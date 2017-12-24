package com.coursera.inversions

import java.util.Arrays.copyOfRange

fun countInversions(arr: IntArray): Int {

    if (arr.size == 1) return 0

    val middle = arr.size / 2
    val leftArr = copyOfRange(arr, 0, middle)
    val lResult = countInversions(leftArr)
    val rightArr = copyOfRange(arr, middle, arr.size)
    val rResult = countInversions(rightArr)

    val splitInversionCount = mergeAndCount(leftArr, rightArr, arr)

    return lResult + rResult + splitInversionCount
}

fun mergeAndCount(leftArr: IntArray, rightArr: IntArray, result: IntArray): Int {
    var middleInversionCnt = 0
    var i = 0
    var j = 0
    var k = 0

    while (i < leftArr.size && j < rightArr.size) {
        if (leftArr[i] < rightArr[j]) {
            result[k++] = leftArr[i++]
        } else {
            middleInversionCnt += leftArr.size - i
            result[k++] = rightArr[j++]
        }
    }

    while (i < leftArr.size) result[k++] = leftArr[i++]
    while (j < rightArr.size) result[k++] = rightArr[j++]

    return middleInversionCnt
}