package one.pre.tasks.t04

import one.pre.common.openFile

fun main() {
    val matrix = read("tasks/t04/input.txt")
    val result1 = solve1(matrix)
    println(result1)

    val result2 = solve2(matrix)
    println(result2)
}

fun solve1(m: List<CharArray>): Int {
    val pattern = arrayOf('X', 'M', 'A', 'S')

    fun compare(i1: Int, j1: Int, i2: Int, j2: Int): Boolean {
        val stepI = (i2 - i1) / pattern.size
        val stepJ = (j2 - j1) / pattern.size

        var i = i1
        var j = j1

        for (idx in pattern.indices) {
            if (i < 0 || j < 0) return false
            if (i >= m.size || j >= m[i].size) return false

            if (m[i][j] != pattern[idx]) return false
            i += stepI
            j += stepJ
        }

        return true
    }

    val directions = arrayOf(
        arrayOf(0, -4),
        arrayOf(4, -4),
        arrayOf(4, 0),
        arrayOf(4, 4),
        arrayOf(0, 4),
        arrayOf(-4, 4),
        arrayOf(-4, 0),
        arrayOf(-4, -4),
    )

    var count = 0
    for (i in m.indices) {
        for (j in m[i].indices) {
            for (direction in directions) {
                if (compare(i, j, i + direction[0], j + direction[1])) {
                    count++
                }
            }
        }
    }

    return count
}

fun solve2(m: List<CharArray>): Int {
    fun isMAS(i: Int, j: Int, direction: Pair<Int, Int>): Boolean {
        val i1 = i + direction.first
        val j1 = j + direction.second
        val i2 = i - direction.first
        val j2 = j - direction.second

        if (i1 < 0 || j1 < 0 || i2 < 0 || j2 < 0) return false
        if (i1 >= m.size || i2 >= m.size || j1 >= m[0].size || j2 >= m[0].size) return false

        return m[i1][j1] == 'M' && m[i2][j2] == 'S' || m[i1][j1] == 'S' && m[i2][j2] == 'M'
    }

    var count = 0
    for (i in m.indices) {
        for (j in m[i].indices) {
            if (m[i][j] != 'A')
                continue

            if (isMAS(i, j, 1 to 1) && isMAS(i, j, 1 to -1)) {
                count++
            }
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