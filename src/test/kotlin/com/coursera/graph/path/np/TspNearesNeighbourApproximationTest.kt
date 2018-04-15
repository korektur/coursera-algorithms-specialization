package com.coursera.graph.path.np

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths


class TspNearesNeighbourApproximationTest {
    companion object {
        const val FILE_NAME = "src/test/resources/tsp_nn.txt"
    }

    @Test
    fun testTspNearestNeighbour() {
        val points = ArrayList<Point>()

        for (line in Files.lines(Paths.get(FILE_NAME)).skip(1)) {
            val (id, x, y) = line.split(" ")
            points.add(Point(x.toDouble(), y.toDouble()))
        }

        val result = TspNearesNeighbourApproximation(points).evaluate()
        assertEquals(1203406, result.toInt())

    }
}