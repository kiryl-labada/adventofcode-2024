package one.pre.tasks.t07

import one.pre.common.openFile
import one.pre.common.parseArray

fun main() {
    val input = read("tasks/t07/input.txt")
    val result = solve(input)
    println(result)
}

fun solve(input: List<Pair<Long, List<Long>>>) =
    input.filter { solve(it.second.size - 1, it.second, it.first) }.sumOf { it.first }

fun solve(i: Int, nums: List<Long>, total: Long): Boolean {
    if (i < 0) return total == 0L
    if (total < 0) return false

    val v = nums[i]
    val part1 = (total % v == 0L && solve(i - 1, nums, total / v))
            || solve(i - 1, nums, total - v)
    if (part1) return true

    val factor = getFactor(v)
    val l = (total - v) / factor
    return (total - v) % factor == 0L
            && solve(i - 1, nums, l)
}

fun getFactor(n: Long): Long {
    var count = 0
    var r = 1L
    var n = n
    while (n != 0L) {
        n /= 10
        r *= 10
        count++
    }

    return r
}

fun read(path: String): List<Pair<Long, List<Long>>> {
    val file = openFile(path)
    return file.readLines()
        .map {
            val parts = it.split(':')
            val total = parts[0].toLong()
            val nums = parseArray<Long>(parts[1].trim())
            total to nums
        }
}