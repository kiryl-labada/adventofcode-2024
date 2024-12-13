package one.pre.tasks.t12

import one.pre.common.openFile
import java.util.*

fun main() {
    val m = read()
    val result: Long = solve(m)
    println(result)
}

fun solve(m: List<List<Char>>): Long {
    val visited = mutableSetOf<Pair<Int, Int>>()

    val directions = arrayOf(
        0 to 1,
        0 to -1,
        1 to 0,
        -1 to 0
    )

    fun inBoundaries(pos: Pair<Int, Int>) = pos.first >= 0 && pos.second >= 0 && pos.first < m.size && pos.second < m[0].size
    fun add(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>) = pos1.first + pos2.first to pos1.second + pos2.second
    fun same(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>) = m[pos1.first][pos1.second] == m[pos2.first][pos2.second]

    fun searchRegion(startI: Int, startJ: Int): Pair<Long, Long> {
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        val region = mutableSetOf<Pair<Int, Int>>()

        queue.add(startI to startJ)
        region.add(startI to startJ)

        while (!queue.isEmpty()) {
            val pos = queue.remove()

            for (dir in directions) {
                val nextPos = add(pos, dir)
                if (inBoundaries(nextPos) && same(pos, nextPos) && !visited.contains(nextPos) && !region.contains(nextPos)) {
                    region.add(nextPos)
                    queue.add(nextPos)
                }
            }
        }

        val area = region.size.toLong()

        var perimeter = 0
        for (pos in region) {
            for (dir in directions) {
                val nextPos = add(pos, dir)
                if (!region.contains(nextPos))
                    perimeter++
            }
        }

        val cornerDirs = arrayOf(
            1 to -1,
            1 to 1,
            -1 to 1,
            -1 to -1
        )

        var sides = 0L
        for (pos in region) {
            var corners = 0
            for (cor in cornerDirs) {
                val pos1 = add(pos, cor.first to 0)
                val pos2 = add(pos, 0 to cor.second)
                if (region.contains(add(pos, cor))) {
                    if (!region.contains(pos1) && !region.contains(pos2)) {
                        corners++
                    }
                } else if (region.contains(pos1) == region.contains(pos2)) {
                    corners++
                }
            }
            sides += corners
        }

        visited.addAll(region)
        return area to sides
    }

    var result = 0L
    for (i in m.indices) {
        for (j in m[i].indices) {
            if (visited.contains(i to j))
                continue

            val (area, perimeter) = searchRegion(i, j)
            result += area * perimeter
        }
    }

    return result
}

fun read() = openFile("tasks/t12/input.txt")
    .readLines()
    .map { it.toCharArray().asList() }