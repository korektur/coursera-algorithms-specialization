package com.coursera.graph.path.np

import java.lang.Math.sqrt

class TspNearesNeighbourApproximation(val points: List<Point>) {

    companion object {
        final val INF = Double.MAX_VALUE
    }

    public fun evaluate(): Double {
        val visited = HashSet<Point>()

        var current = points[0]
        visited.add(current)
        var ans = 0.0
        while (visited.size < points.size) {
            var curMin = INF
            var curMinPoint = current
            for (point in points) {
                if (!visited.contains(point) && current.squareDistTo(point) < curMin) {
                    curMin = current.squareDistTo(point)
                    curMinPoint = point
                }
            }

            visited.add(curMinPoint)
            current = curMinPoint
            ans += sqrt(curMin)
        }

        ans += current.distTo(points[0])

        return ans
    }
}