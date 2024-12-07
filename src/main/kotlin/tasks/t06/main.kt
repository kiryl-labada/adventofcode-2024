package one.pre.tasks.t06

import one.pre.common.openFile

fun main() {
    val m = read("tasks/t06/input.txt")
    val result = solve2(m)
    println(result)
}

class FoundCycleException : Exception()

fun solve1(m: List<CharArray>): Int {
    val visited = mutableListOf<BooleanArray>()
    for (i in m.indices)
        visited.add(BooleanArray(m[i].size))

    fun findInitialPosition(): Pair<Int, Int> {
        for (i in m.indices) {
            for (j in m[i].indices) {
                if (m[i][j] == '^') {
                    return i to j
                }
            }
        }

        throw Exception("Not found")
    }

    val initPosition = findInitialPosition()
    var (i, j) = initPosition
    val directions = arrayOf(
        -1 to 0,
        0 to 1,
        1 to 0,
        0 to -1
    )
    var direction = 0
    val walls = mutableSetOf<Triple<Int, Int, Int>>()

    fun makeStep(): Boolean {
        val (diffI, diffJ) = directions[direction]
        val nextI = i + diffI
        val nextJ = j + diffJ
        if (nextI < 0 || nextI >= m.size) return false
        if (nextJ < 0 || nextJ >= m[nextI].size) return false

        if (m[nextI][nextJ] == '#') {
            if (walls.contains(Triple(nextI, nextJ, direction)))
                throw FoundCycleException()

            walls.add(Triple(nextI, nextJ, direction))

            direction = (direction + 1) % directions.size
            return true
        }

        i = nextI
        j = nextJ
        visited[i][j] = true
        return true
    }

    while (makeStep()) {}

    return visited.sumOf { it.count { it } }
}

fun solve2(m: List<CharArray>): Int {
    var count = 0
    for (i in m.indices) {
        for (j in m[i].indices) {
            val c = m[i][j]
            if (c != '.') continue

            m[i][j] = '#'
            try {
                solve1(m)
            } catch (e: FoundCycleException) {
                count++
            }

            m[i][j] = c
        }
    }

    return count
}

fun read(path: String): List<CharArray> {
    val file = openFile(path)
    return file
        .readLines()
        .map { it.toCharArray() }
}