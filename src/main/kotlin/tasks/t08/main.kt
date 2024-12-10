package one.pre.tasks.t08

import one.pre.common.openFile

fun main() {
    val m = read("tasks/t08/input.txt")
    val result: Int = solve(m)
    println(result)
}

fun solve(m: List<CharArray>): Int {
    val map = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    for (i in m.indices) {
        for (j in m[i].indices) {
            val v = m[i][j]
            if (v != '.') {
                map[v] = map[v] ?: mutableListOf()
                map[v]!!.add(i to j)
            }
        }
    }

    fun withinBound(point: Pair<Int, Int>) =
        point.first >= 0 && point.first < m.size && point.second >= 0 && point.second < m[0].size

    val antinodes = mutableSetOf<Pair<Int, Int>>()
    for ((signal, locations) in map) {
        for (loc1 in locations) {
            for (loc2 in locations) {
                if (loc1 == loc2) continue
                if (loc1.second > loc2.second) continue
                if (loc1.second == loc2.second && loc1.first < loc2.first) continue

                val diffI = loc2.first - loc1.first
                val diffJ = loc2.second - loc1.second

                var antinode1 = loc1
                while (withinBound(antinode1)) {
                    antinodes.add(antinode1)
                    antinode1 = antinode1.first - diffI to antinode1.second - diffJ
                }

                var antinode2 = loc2
                while (withinBound(antinode2)) {
                    antinodes.add(antinode2)
                    antinode2 = antinode2.first + diffI to antinode2.second + diffJ
                }
//
//                val antinode1 = loc2.first + diffI to loc2.second + diffJ
//                val antinode2 = loc1.first - diffI to loc1.second - diffJ
//
//                if (withinBound(antinode1)) antinodes.add(antinode1)
//                if (withinBound(antinode2)) antinodes.add(antinode2)
            }
        }
    }

    for (i in m.indices) {
        for (j in m[i].indices) {
            if (antinodes.contains(i to j)) {
                print('#')
            } else {
                print(m[i][j])
            }
        }
        println()
    }

    return antinodes.size
}

fun read(path: String) = openFile(path).readLines().map { it.toCharArray() }
