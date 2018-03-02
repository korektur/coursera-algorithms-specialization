package com.coursera.hashtables

import java.util.*
import kotlin.collections.HashSet

/**
 * Solves 2SUM problem:
 * Given a set `S` of numbers, and a number `t` check if there are two numbers `a` and `b` in `S`, that `a + b = t`
 *
 * This particular implementation for given set `S` range `[a,b]` returns how many numbers in given range has
 * 2SUM solution with distict numbers `a` and `b` in `S`.
 */
class TwoSumProblemResolver(arr: LongArray) {

    private val storage = HashSet<Long>()
    private val arr: LongArray

    init {
        this.arr = arr.sortedArray().distinct().toLongArray()
        Arrays.sort(this.arr)
        arr.forEach { storage.add(it) }
    }

    public fun hasSupplements(range: LongRange): Int {
        val hits = HashSet<Long>()
        for (elem in arr) {
            val left_bound = range.first - elem
            val right_bound = range.last - elem

            for (i in left_bound..right_bound) {
                if (i != elem && storage.contains(i)) {
                    hits.add(elem + i)
                }
            }
        }

        return hits.size
    }
}