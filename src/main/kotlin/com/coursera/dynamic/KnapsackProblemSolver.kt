package com.coursera.dynamic

import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.math.max

public data class Item(public val weight: Int, public val value: Long)

public fun solve(capacity: Int, items: List<Item>): Long {
    val d = Array(items.size + 1, { LongArray(capacity + 1) })
    for (k in 1..items.size) {
        for (s in 1..capacity) {
            if (s >= items[k - 1].weight) {
                d[k][s] = max(d[k - 1][s], d[k - 1][s - items[k - 1].weight] + items[k - 1].value)
            } else {
                d[k][s] = d[k - 1][s]
            }
        }
    }

    return d[items.size][capacity]
}
