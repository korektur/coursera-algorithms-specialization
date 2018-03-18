package com.coursera.dynamic

class MaxWeightPathEvaluator(val weights: LongArray) {

    val d = LongArray(weights.size)
    val path = HashSet<Int>()
    var maxPathWeight = -1L

    public fun evaluate(): Long {
        if (maxPathWeight != -1L) {
            return maxPathWeight
        }

        d[0] = weights[0]
        if (weights.size == 1) {
            return weights[0]
        }

        d[1] = weights[1]
        for (i in 2 until weights.size) {
            val weight1 = d[i - 1]
            val weight2 = d[i - 2] + weights[i]
            if (weight1 > weight2) {
                d[i] = weight1
            } else {
                d[i] = weight2
            }
        }

        return d.last()
    }

    public fun getPath(): Set<Int> {
        if (path.isNotEmpty()) {
            return path
        }

        if (maxPathWeight == -1L) {
            evaluate()
        }

        var i = d.lastIndex
        while (i >= 2) {
            if (d[i] == weights[i] + d[i - 2]) {
                path.add(i + 1)
                i -= 2
            } else if (d[i] == d[i - 1]) {
                --i
            } else {
                throw IllegalStateException("invalid state at $i")
            }
        }

        path.add(i + 1)
        return path
    }

}