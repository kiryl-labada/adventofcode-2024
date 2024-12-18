package one.pre.tasks.t16

import one.pre.common.*
import java.util.PriorityQueue
import kotlin.math.min

fun main() {
    val m = read()
    val (part1, part2) = solve(m)
    println(part1)
    println(part2)
}

fun read() = openFile("tasks/t16/input.txt")
    .readLines()
    .map { it.toCharArray().toList() }

fun solve(m: List<List<Char>>): Pair<Long, Long> {
    val s = find(m, 'S')
    val e = find(m, 'E')

    val queue = PriorityQueue<Triple<Long, Point, Direction>>({ a, b -> a.first.compareTo(b.first) })
    val costs = mutableMapOf<Pair<Point, Direction>, Long>()
    costs[s to right] = 0

    fun add(current: Point, direction: Direction, cost: Long) {
        val next = addInt(current, direction)
        if (next.first < 0 || next.second < 0 || next.first >= m.size || next.second >= m[0].size || m[next.first][next.second] == '#')
            return

        val curCost = costs[next to direction]
        if (curCost != null && curCost < cost)
            return

        queue.add(Triple(cost, next, direction))
        costs[next to direction] = cost
    }

    queue.add(Triple(0, s, right))

    while (queue.isNotEmpty()) {
        val (cost, p, dir) = queue.remove()

        add(p, dir, cost + 1)
        add(p, dir.turnRight(), cost + 1000 + 1)
        add(p, dir.turnLeft(), cost + 1000 + 1)
        add(p, dir.turnAround(), cost + 1000 + 1000 + 1)
    }

    val part1 = directions.minOf { costs.get(e to it) ?: Long.MAX_VALUE }

    val path = mutableSetOf<Point>()
    fun findPath(p: Point, dir: Direction) {
        path.add(p)
        val cost = costs[p to dir]!!
        val prev = addInt(p, dir.turnAround())
        if (costs[prev to dir] == cost - 1) {
            findPath(prev, dir)
        }
        if (costs[prev to dir.turnRight().turnAround()] == cost - 1 - 1000) {
            findPath(prev, dir.turnRight().turnAround())
        }
        if (costs[prev to dir.turnLeft().turnAround()] == cost - 1 - 1000) {
            findPath(prev, dir.turnLeft().turnAround())
        }
        if (costs[prev to dir.turnAround()] == cost - 1 - 1000 - 1000) {
            findPath(prev, dir.turnAround())
        }
    }

    directions.filter { costs[e to it] == part1 }.forEach { findPath(e, it)  }
    val part2 = path.size

    return part1 to part2.toLong()
}

fun clone(m: List<List<Char>>, default: Long): List<MutableList<Long>> {
    val t = mutableListOf<MutableList<Long>>()
    for (i in m.indices) {
        t.add(MutableList(m[i].size) { default })
    }
    return t
}