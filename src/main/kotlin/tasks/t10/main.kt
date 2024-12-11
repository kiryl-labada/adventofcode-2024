package one.pre.tasks.t10

import one.pre.common.openFile
import java.util.*

fun main() {
    val m = read()
    val (scores, ratings) = solve1(m)
    println(scores.sum())
    println(ratings.sum())
}

fun solve1(m: List<List<Int>>): Pair<List<Int>, List<Int>> {
    val scores = mutableListOf<Int>()
    val ratings = mutableListOf<Int>()

    val directions = listOf(
        0 to 1,
        0 to -1,
        1 to 0,
        -1 to 0
    )

    fun bfs(i: Int, j: Int): Set<Pair<Int, Int>> {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.add(i to j)

        while (!queue.isEmpty()) {
            val (i, j) = queue.remove()
            val v = m[i][j]

            for (direction in directions) {
                val (nextI, nextJ) = i + direction.first to j + direction.second
                if (nextI < 0 || nextJ < 0 || nextI >= m.size || nextJ >= m[0].size || visited.contains(nextI to nextJ))
                    continue

                val nextV = m[nextI][nextJ]
                if (v + 1 != nextV) continue

                queue.add(nextI to nextJ)
                visited.add(nextI to nextJ)
            }
        }

        return visited
    }

    fun dfs(i: Int, j: Int): Int {
        val stack: Stack<Pair<Int, Int>> = Stack()
        stack.push(i to j)
        var rating = 0

        while (!stack.isEmpty()) {
            val (i, j) = stack.pop()
            val v = m[i][j]
            if (v == 9) {
                rating++
                continue
            }

            for (direction in directions) {
                val (nextI, nextJ) = i + direction.first to j + direction.second
                if (nextI < 0 || nextJ < 0 || nextI >= m.size || nextJ >= m[0].size)
                    continue

                val nextV = m[nextI][nextJ]
                if (v + 1 != nextV) continue

                stack.add(nextI to nextJ)
            }
        }

        return rating
    }

    for (i in m.indices) {
        for (j in m[i].indices) {
            if (m[i][j] != 0) continue

            val visited = bfs(i, j)
            val score = visited.count { m[it.first][it.second] == 9 }
            if (score != 0) scores.add(score)

            val rating = dfs(i, j)
            if (rating != 0) ratings.add(rating)
        }
    }

    return scores to ratings
}

fun read() = openFile("tasks/t10/input.txt")
    .readLines()
    .map { it.toCharArray().map { it.digitToInt() } }