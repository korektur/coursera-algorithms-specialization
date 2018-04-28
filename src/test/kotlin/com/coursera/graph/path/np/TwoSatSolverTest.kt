package com.coursera.graph.path.np

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

class TwoSatSolverTest {

    companion object {
        val TEST_CASES_PATH = "src/test/resources/2sat/testCases"
    }

    @Test
    fun simpleTest() {
        assertTrue(TwoSatSolver(listOf(Pair(1, 2))).solve())
    }

    @Test
    fun simpleTest2() {
        assertTrue(TwoSatSolver(listOf(Pair(1, 2),
                Pair(2, -1),
                Pair(-1, -2))).solve())

    }

    @Test
    fun simpleTestFalse() {
        assertFalse(TwoSatSolver(listOf(Pair(1, 2),
                Pair(-1, 2),
                Pair(1, -2),
                Pair(-1, -2))).solve())

    }

    @Test
    fun simpleTest3() {
        assertTrue(TwoSatSolver(listOf(
                Pair(1, 2),
                Pair(1, 3),
                Pair(-2, 3),
                Pair(-2, 1))).solve())

    }

    @Test
    fun simpleTest4False() {
        assertFalse(TwoSatSolver(listOf(
                Pair(-1, 3),
                Pair(-3, -1),
                Pair(1, 2),
                Pair(-2, 1))).solve())

    }


    @Test
    fun simpleTest5() {
        assertTrue(TwoSatSolver(listOf(
                Pair(-1, -2),
                Pair(2, 2))).solve())

    }


    @ParameterizedTest
    @ValueSource(strings = ["src/test/resources/2sat/2sat1.txt",
        "src/test/resources/2sat/2sat2.txt",
        "src/test/resources/2sat/2sat3.txt",
        "src/test/resources/2sat/2sat4.txt",
        "src/test/resources/2sat/2sat5.txt",
        "src/test/resources/2sat/2sat6.txt"])
    fun testSolve(filePath: String) {
        val input = Files.lines(Paths.get(filePath))
                .skip(1)
                .map { it.split(" ") }
                .map { Pair(it[0].toInt(), it[1].toInt()) }
                .collect(Collectors.toList())
        val result = TwoSatSolver(input).solve()

        println(result)
    }

    @Test
    fun testSolveTestCases() {
        val files = Paths.get(TEST_CASES_PATH).toFile().listFiles().toList()
        val input = files.stream().filter { it.name.startsWith("input") }.collect(Collectors.toList())
        val output = files.stream().filter { it.name.startsWith("output") }.collect(Collectors.toList())

        for (i in 0 until input.size) {
            val inputData = Files.lines(input[i].toPath())
                    .skip(1)
                    .map { it.split(" ") }
                    .map { Pair(it[0].toInt(), it[1].toInt()) }
                    .collect(Collectors.toList())
            val result = TwoSatSolver(inputData).solve()
            println(input[i].name + " " + output[i].name)
            assertEquals(Files.lines(output[i].toPath()).limit(1).map { it == "1" }.findFirst().get(), result)
        }
    }
}