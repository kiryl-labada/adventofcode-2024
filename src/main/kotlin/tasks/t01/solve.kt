package one.pre.tasks.t01

import one.pre.common.openFile
import one.pre.common.parsePair
import kotlin.math.abs

fun main() {
    val (arr1, arr2) = read("tasks/t01/input.txt")
    val result1 = solve1(arr1, arr2)
    val result2 = solve2(arr1, arr2)
    println(result1)
    println(result2)
}

fun solve1(arr1: MutableList<Int>, arr2: MutableList<Int>): Int {
    arr1.sort()
    arr2.sort()

    return arr1
        .zip(arr2) { a, b -> abs(a - b) }
        .sum()
}

fun solve2(arr1: MutableList<Int>, arr2: MutableList<Int>): Int {
    arr1.sort()
    arr2.sort()

    var idx = 0

    val result = mutableListOf<Int>()

    arr1.forEachIndexed { i, v ->
        if (i > 0 && arr1[i - 1] == v) {
            result.add(result.last())
            return@forEachIndexed
        }

        while (idx < arr2.size && v > arr2[idx]) idx++

        var count = 0
        while (idx < arr2.size && v == arr2[idx]) {
            count++
            idx++
        }

        result.add(count * v)
    }

    return result.sum()
}

fun read(path: String): Pair<MutableList<Int>, MutableList<Int>> {
    val file = openFile(path)

    val arr1 = mutableListOf<Int>()
    val arr2 = mutableListOf<Int>()

    file.readLines()
        .map { parsePair<Int, Int>(it) }
        .forEach {
            arr1.add(it.first)
            arr2.add(it.second)
        }

    return arr1 to arr2
}