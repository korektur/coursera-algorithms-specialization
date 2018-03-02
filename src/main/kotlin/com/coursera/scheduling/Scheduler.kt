package com.coursera.scheduling

data class Job(val weight: Long, var length: Int)

enum class Strategy(val comparator: Comparator<Job>) {
    DIFFERENCE(Comparator(::differenceComparator)),
    RATIO(Comparator(::ratioComparator));
}

private fun differenceComparator(o1: Job, o2: Job): Int {
    val res = -(o1.weight - o1.length).compareTo(o2.weight - o2.length)
    if (res != 0) {
        return res
    }

    return -o1.weight.compareTo(o2.weight)
}


private fun ratioComparator(o1: Job, o2: Job): Int {
    val res = -(o1.weight / o1.length.toDouble()).compareTo(o2.weight /  o2.length.toDouble())
    if (res != 0) {
        return res
    }

    val weightRes = -o1.weight.compareTo(o2.weight)
    if (weightRes != 0) {
        return weightRes
    }

    return -o1.length.compareTo(o2.length)
}

public fun schedule(jobs: List<Job>, strategy: Strategy): Long {
    val sorted = ArrayList<Job>(jobs)
    sorted.sortWith(strategy.comparator)
    var result = 0L
    var timer = 0L

    for (i in 0 until sorted.size) {
        timer += sorted[i].length
        result += timer * sorted[i].weight
    }

    return result
}