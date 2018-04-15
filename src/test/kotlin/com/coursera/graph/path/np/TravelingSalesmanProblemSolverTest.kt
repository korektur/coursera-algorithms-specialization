package com.coursera.graph.path.np

import com.coursera.graph.path.np.TravelingSalesmanProblemSolver.Companion.INF
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors.toList


class TravelingSalesmanProblemSolverTest {

    companion object {
        const val FILE_NAME = "src/test/resources/tsp.txt"
    }

    @Test
    fun testTspSmallCliqueGraph() {
        val inputGraph = arrayOf(doubleArrayOf(0.0, 1.0, 2.0, 1.0),
                doubleArrayOf(1.0, 0.0, 1.0, 2.0),
                doubleArrayOf(2.0, 1.0, 0.0, 1.0),
                doubleArrayOf(1.0, 2.0, 1.0, 0.0))

        val tspSolver = TravelingSalesmanProblemSolver(inputGraph)

        val result = runBlocking {
            tspSolver.evaluate()
        }

        assertEquals(4, result.toInt())
    }

    @Test
    fun testTspSmallGraph() {
        val inputGraph = arrayOf(doubleArrayOf(0.0, 10.0, 20.0, 15.0),
                doubleArrayOf(10.0, 0.0, 35.0, 25.0),
                doubleArrayOf(15.0, 35.0, 0.0, 30.0),
                doubleArrayOf(20.0, 25.0, 30.0, 0.0))

        val tspSolver = TravelingSalesmanProblemSolver(inputGraph)

        val result = runBlocking {
            tspSolver.evaluate()
        }

        assertEquals(80, result.toInt())
    }


    @Test
    fun testTspSmallPathGraph() {
        val inputGraph = arrayOf(
                doubleArrayOf(0.0, 1.0, INF, 1.0),
                doubleArrayOf(1.0, 0.0, 1.0, INF),
                doubleArrayOf(INF, 1.0, 0.0, 1.0),
                doubleArrayOf(1.0, INF, 1.0, 0.0))

        val tspSolver = TravelingSalesmanProblemSolver(inputGraph)

        val result = runBlocking { tspSolver.evaluate() }

        assertEquals(4, result.toInt())
    }

    @Test
    fun testTspBigGraph() {
        val input = Files.lines(Paths.get(FILE_NAME))
                .skip(1)
                .map { it.split(" ") }
                .map { Point(it[0].toDouble(), it[1].toDouble()) }
                .collect(toList())

        val fstMedianPoint = input[12]
        val sndMedianPoint = input[13]
        val leftPart = input.stream()
                .filter { it.x <= sndMedianPoint.x }
                .collect(toList())

        val rightPart = input.stream()
                .filter { it.x >= fstMedianPoint.x }
                .collect(toList())

        println(rightPart.size)
        println(leftPart.size)
        val leftMatrix = toEdgeMatrix(leftPart)
        val rightMatrix = toEdgeMatrix(rightPart)


        val leftResult = async {
            println("rightPart")
            TravelingSalesmanProblemSolver(leftMatrix).evaluate()
        }

        val rightResult = async {
            println("leftPart")
            TravelingSalesmanProblemSolver(rightMatrix).evaluate()
        }

        runBlocking {
            println(leftResult.await())
            println(rightResult.await())

            println(leftResult.await() + rightResult.await())
            val ans = leftResult.getCompleted() + rightResult.getCompleted() - fstMedianPoint.distTo(sndMedianPoint)
//            - fstMedianPoint.distTo(input[11]) - sndMedianPoint.distTo(input[14]) +
//                    fstMedianPoint.distTo(sndMedianPoint) + input[11].distTo(input[14])
            println(ans)
        }

        println(runBlocking { TravelingSalesmanProblemSolver(toEdgeMatrix(input)).evaluate() }

        )
    }


    private fun toEdgeMatrix(points: List<Point>): Array<DoubleArray> {
        val n = points.size
        val edgeMatrix = Array(n, { DoubleArray(n) })
        for (i in 0 until n) {
            for (j in i + 1 until n) {
                val dist = points[i].distTo(points[j])
                edgeMatrix[i][j] = dist
                edgeMatrix[j][i] = dist
            }
        }

        return edgeMatrix
    }
}