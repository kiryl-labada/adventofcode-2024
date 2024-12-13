package one.pre.tasks.t11

import one.pre.common.openFile
import one.pre.common.parseArray
import java.lang.StrictMath.multiplyExact

fun main() {
    val nums = read()
//    val nums = listOf(1)
    val result = solve(nums, 75)
    println(result)
}

fun solve(nums: List<Int>, count: Int): ULong {
    fun split(v: ULong): Pair<ULong, ULong> {
        val vs = v.toString()
        val left = vs.substring(0, vs.length / 2).toULong()
        val right = vs.substring(vs.length / 2, vs.length).toULong()

        return left to right
    }

    val cache = mutableMapOf<Pair<ULong, Int>, ULong>()

    fun run(v: ULong, steps: Int): ULong {
        if (steps == 0) return 1UL
        if (cache.contains(v to steps)) return cache[v to steps]!!

        val result = when {
            v == 0UL -> run(1UL, steps - 1)
            v >= 10UL && v.toString().length % 2 == 0 -> split(v).let { run(it.first, steps - 1) + run(it.second, steps - 1) }
            else -> run(v * 2024UL, steps - 1)
        }
        cache[v to steps] = result
        return result
    }

    var s = 0UL
    for (num in nums) {
        println(num)
        s += run(num.toULong(), count)
    }

    println("result")
    return s
}

fun read() = openFile("tasks/t11/input.txt")
    .readText()
    .let { parseArray<Int>(it) }
