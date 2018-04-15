package com.coursera.graph.path.np

public data class Point(public val x: Double, public val y: Double) {
    public fun distTo(other: Point): Double {
        return Math.sqrt(squareDistTo(other))
    }

    public fun squareDistTo(other: Point): Double {
        return Math.pow(x - other.x, 2.0) + Math.pow(y - other.y, 2.0)
    }

}