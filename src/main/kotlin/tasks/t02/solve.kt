package one.pre.tasks.t02

import one.pre.common.openFile
import one.pre.common.parseArray
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val reports = read("tasks/t02/input.txt")
    println(reports.map { isSafe(it) }.count { it })
    println(reports.map { isSafeWithTolerance(it) }.count { it })
}

fun isSafe(report: List<Int>): Boolean {
    val inc = report[1] > report[0]
    val min = if (inc) 1 else -3
    val max = if (inc) 3 else -1

    for (i in 1 until report.size) {
        val diff = report[i] - report[i - 1]
        if (diff !in min..max) {
            return false
        }
    }

    return true
}

fun isSafeWithTolerance(report: List<Int>): Boolean {
    fun isSafe(index: Int, prev: Int?, prevDiff: Int?, allowErrors: Int): Boolean {
        if (allowErrors < 0)
            return false

        if (index >= report.size)
            return true

        val cur = report[index]
        if (prev != null) {
            val diff = cur - prev

            if (prevDiff != null && diff.sign != prevDiff.sign) {
                return isSafe(index + 1, prev, prevDiff, allowErrors - 1)
            }

            if (abs(diff) !in 1..3) {
                return isSafe(index + 1, prev, prevDiff, allowErrors - 1)
            }

            return isSafe(index + 1, cur, diff, allowErrors)
                    || isSafe(index + 1, prev, prevDiff, allowErrors - 1)
        }

        return isSafe(index + 1, prev, prevDiff, allowErrors - 1)
                || isSafe(index + 1, cur, null, allowErrors)
    }

    return isSafe(0, null, null, 1)
}

fun read(path: String): List<List<Int>> {
    val file = openFile(path)

    return file
        .readLines()
        .map { parseArray<Int>(it) }
}