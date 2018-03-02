package com.coursera.scheduling

import com.coursera.scheduling.Strategy.DIFFERENCE
import com.coursera.scheduling.Strategy.RATIO
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader


internal class SchedulerKtTest {

    companion object {
        private val FILE_NAME = "src/test/resources/jobs.txt"
    }

    @Test
    fun testScheduleByDiff() {
        val input = BufferedReader(FileReader(FILE_NAME))
        val size = input.readLine().toInt()

        val jobs = ArrayList<Job>(size)

        for (i in 1 .. size) {
            val (weightStr, lengthStr) = input.readLine().split(" ")
            jobs.add(Job(weightStr.toLong(), lengthStr.toInt()))
        }

        println(schedule(jobs, DIFFERENCE))
    }


    @Test
    fun testScheduleByRatio() {
        val input = BufferedReader(FileReader(FILE_NAME))
        val size = input.readLine().toInt()

        val jobs = ArrayList<Job>(size)

        for (i in 1 .. size) {
            val (weightStr, lengthStr) = input.readLine().split(" ")
            jobs.add(Job(weightStr.toLong(), lengthStr.toInt()))
        }

        println(schedule(jobs, RATIO))
    }
}