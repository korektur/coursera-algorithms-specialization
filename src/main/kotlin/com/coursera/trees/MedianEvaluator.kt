package com.coursera.trees

/**
 * Calculates median of a given values using two heaps.
 */
class MedianEvaluator<T>(private val comparator: Comparator<T>) {

    private val minHeap = Heap(comparator)
    private val maxHeap = Heap<T>(comparator.reversed())

    public fun add(elem: T) {
        if (maxHeap.size() == 0 || comparator.compare(elem, maxHeap.peek()) < 0) {
            maxHeap.add(elem)
        } else {
            minHeap.add(elem)
        }

        while (maxHeap.size() - minHeap.size() > 1) {
            minHeap.add(maxHeap.poll()!!)
        }

        while (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll()!!)
        }
    }

    public fun getMedian(): T? {
        if (minHeap.size() > maxHeap.size()) {
            return minHeap.peek()
        }

        return maxHeap.peek()
    }
}