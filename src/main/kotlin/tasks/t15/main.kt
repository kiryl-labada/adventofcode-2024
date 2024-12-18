package one.pre.tasks.t15

import one.pre.common.*

fun main() {
    val (map, seq) = read()
    val result1: Long = part1(map, seq)
    println(result1)

    val result2: Long = part2(transform(map), seq)
    println(result2)
}

fun read() = openFile("tasks/t15/input.txt")
    .readLines()
    .let { lines ->
        val idx = lines.indexOf("")
        val table = lines.subList(0, idx).map { it.toCharArray().toMutableList() }
        val seq = lines.subList(idx + 1, lines.size).joinToString("").toCharArray().asList()
        table to seq
    }

fun transform(map: List<MutableList<Char>>): List<MutableList<Char>> {
    val r = mutableListOf<MutableList<Char>>()
    for (i in map.indices) {
        val row = mutableListOf<Char>()
        for (j in map[i].indices) {
            val v = map[i][j]
            when (v) {
                '#' -> {
                    row.add('#')
                    row.add('#')
                }
                'O' -> {
                    row.add('[')
                    row.add(']')
                }
                '.' -> {
                    row.add('.')
                    row.add('.')
                }
                '@' -> {
                    row.add('@')
                    row.add('.')
                }
            }
        }
        r.add(row)
    }

    return r
}

fun List<List<Char>>.get(pos: Point) = this[pos.first][pos.second]
fun List<MutableList<Char>>.swap(pos1: Point, pos2: Point) {
    val tmp = get(pos1)
    this[pos1.first][pos1.second] = get(pos2)
    this[pos2.first][pos2.second] = tmp
}

val dirs = mapOf(
    '^' to (-1 to 0),
    '>' to (0 to 1),
    'v' to (1 to 0),
    '<' to (0 to -1),
)

fun gps(map: List<MutableList<Char>>): Long {
    var result = 0L
    for (i in map.indices) {
        for (j in map[i].indices) {
            val v = map[i][j]
            if (v == 'O' || v == '[') {
                result += i * 100 + j
            }
        }
    }

    return result
}

fun part1(map: List<MutableList<Char>>, seq: List<Char>): Long {
    var pos = find(map, '@')

    for (move in seq.map(dirs::get)) {
        var head = pos
        do {
            head = addInt(head, move!!)
        } while (map.get(head).let { it != '.' && it != '#' })

        if (map.get(head) == '#') continue

        do {
            val next = sub(head, move)
            map.swap(head, next)
            head = next
        } while (head != pos)

        pos = addInt(pos, move)
    }

    return gps(map)
}

fun part2(map: List<MutableList<Char>>, seq: List<Char>): Long {
    var pos = find(map, '@')

    fun tryMove(pos: Point, move: Point): Boolean {
        val toMove = mutableListOf<Point>()
        var curLayer = mutableSetOf(pos)
        var nextLayer = mutableSetOf<Point>()

        val vertical = move.second == 0

        do {
            while (curLayer.isNotEmpty()) {
                val pos = curLayer.first()
                curLayer.remove(pos)
                if (map.get(pos) == '#') return false
                if (map.get(pos) == '.') continue
                toMove.add(pos)

                val next = addInt(pos, move)
                nextLayer.add(next)
                if (!vertical) continue

                val v = map.get(next)
                if (v == '[') nextLayer.add(next.first to next.second + 1)
                else if (v == ']') nextLayer.add(next.first to next.second - 1)
            }

            val tmp = curLayer
            curLayer = nextLayer
            nextLayer = tmp
        } while (curLayer.isNotEmpty())

        for (i in toMove.size - 1 downTo 0) {
            val cur = toMove[i]
            val next = addInt(cur, move)
            map.swap(cur, next)
        }

        return true
    }

    for (move in seq.map(dirs::get)) {
        if (tryMove(pos, move!!)) {
            pos = addInt(pos, move)
        }
    }

    return gps(map)
}