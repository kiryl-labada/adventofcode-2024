package one.pre.tasks.t14

import one.pre.common.openFile

fun main() {
    val input = read()
//    val result = solve(11, 7, input, 100)
//    println(result)

    for (i in 1000..10000) {
        solve(101, 103, input, i)
    }
}

fun solve(w: Long, h: Long, robots: List<List<Long>>, steps: Int): Long {
    val midW = w / 2
    val midH = h / 2

    val positions = mutableSetOf<Pair<Long, Long>>()
    val counts = mutableMapOf(
        false to mutableMapOf(false to 0L, true to 0L),
        true to mutableMapOf(false to 0L, true to 0L)
    )
    fun count(x: Long, y: Long) {
        if (x == midW || y == midH) return
        val tmp = counts[x > midW]!!
        tmp[y > midH] = tmp[y > midH]!! + 1
    }

    for (r in robots) {
        val x = r[0]
        val y = r[1]
        val vx = r[2]
        val vy = r[3]

        var fx = (x + vx * steps) % w
        var fy = (y + vy * steps) % h
        fx = (fx + w) % w
        fy = (fy + h) % h
        count(fx, fy)

        positions.add(fy to fx)
    }

    var shouldPrint = false
    for (pos in positions) {
        var skip = false
        for (i in 0..5) {
            if (!positions.contains(pos.first to pos.second + i)) {
                skip = true
                break
            }
        }

        if (!skip) {
            shouldPrint = true
            break
        }
    }

    if (shouldPrint) {
        for (i in 0 until h) {
            for (j in 0 until w) {
                if (positions.contains(i to j)) {
                    print(':')
                } else {
                    print('.')
                }
            }
            println()
        }
        println(steps)
        Thread.sleep(2000)
        println("\n\n\n\n")
    }

    val t = counts.values.map { it.values }.flatten()
    return t[0] * t[1] * t[2] * t[3]
}

val regex = "(-?\\d+)".toRegex()
fun read() = openFile("tasks/t14/input.txt")
    .readLines()
    .map { regex.findAll(it).map{ it.groupValues[1].toLong() }.toList() }