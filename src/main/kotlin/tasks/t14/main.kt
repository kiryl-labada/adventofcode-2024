package one.pre.tasks.t14

import one.pre.common.addLong
import one.pre.common.directions
import one.pre.common.openFile

fun main() {
    val input = read()
    val result = part1(101, 103, input, 100)
    println(result)

    (1000..10000)
        .find { part2(101, 103, input, it) }
        ?.let { println(it) }
}

fun getPositions(w: Long, h: Long, robots: List<List<Long>>, steps: Int): Sequence<Pair<Long, Long>> = robots.asSequence().map { r ->
    val x = r[0]
    val y = r[1]
    val vx = r[2]
    val vy = r[3]

    var fx = (x + vx * steps) % w
    var fy = (y + vy * steps) % h
    fx = (fx + w) % w
    fy = (fy + h) % h
    fy to fx
}

fun part1(w: Long, h: Long, robots: List<List<Long>>, steps: Int): Long {
    val midW = w / 2
    val midH = h / 2

    return getPositions(w, h, robots, steps)
        .filter { (y, x) -> x != midW && y != midH }
        .groupBy { (y, x) -> (x > midW) to (y > midH) }
        .values.map { it.count().toLong() }
        .reduce { a, b -> a * b }
}

fun part2(w: Long, h: Long, robots: List<List<Long>>, steps: Int): Boolean {
    val positions = getPositions(w, h, robots, steps).toSet()
    val dirs = directions.map {it.first.toLong() to it.second.toLong()}
    return positions
        .asSequence()
        .filter { p ->
            dirs.all { positions.contains(addLong(p, it)) }
        }
        .drop(5)
        .any()
}

val regex = "(-?\\d+)".toRegex()
fun read() = openFile("tasks/t14/input.txt")
    .readLines()
    .map { regex.findAll(it).map{ it.groupValues[1].toLong() }.toList() }