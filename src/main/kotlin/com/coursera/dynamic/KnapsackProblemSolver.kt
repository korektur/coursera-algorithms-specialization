package com.coursera.dynamic

import java.util.*
import kotlin.math.max

public data class Item(public val weight: Int, public val value: Long)

public fun solve(capacity: Int, items: List<Item>): Long {
    val d = LinkedList(listOf(LongArray(capacity + 1), LongArray(capacity + 1)))
    for (k in 1..items.size) {
        for (s in 1..capacity) {
            if (s >= items[k - 1].weight) {
                d[1][s] = max(d[0][s], d[0][s - items[k - 1].weight] + items[k - 1].value)
            } else {
                d[1][s] = d[0][s]
            }
        }

        System.arraycopy(d[1], 0, d[0], 0, d[1].size)
    }

    return d[1][capacity]
}
