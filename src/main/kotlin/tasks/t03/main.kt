package one.pre.tasks.t03

import one.pre.common.openFile

fun main() {
    val file = openFile("tasks/t03/input.txt")
    val input = file.readText()
    val result1 = solve1(input)
    println(result1)
    val result2 = solve2(input)
    println(result2)
}

fun solve1(input: String): Int {
    val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
    return regex.findAll(input)
        .map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
        .sum()
}

fun solve2(input: String): Int {
    val regex = """mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""".toRegex()
    var enabled = true
    var result = 0
    regex.findAll(input)
        .forEach {
            when (it.value) {
                "do()" -> enabled = true
                "don't()" -> enabled = false
            }

            if (enabled && it.value.startsWith("mul")) {
                result += it.groupValues[1].toInt() * it.groupValues[2].toInt()
            }
        }

    return result
}