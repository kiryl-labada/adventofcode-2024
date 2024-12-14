package one.pre.tasks.t13

import one.pre.common.*

fun main() {
    val (buttonA, buttonB, prize) = read()

    var result = 0L
    for (i in buttonA.indices) {
        val r = solve(buttonA[i], buttonB[i], add(prize[i], 10000000000000L to 10000000000000L))
        if (r != null) result += r
    }
    println(result)
}

fun solve(a1: Long, a2: Long, b1: Long, b2: Long, p1: Long, p2: Long): Pair<Long?, Long?> {
    val x1 = b2 * p1 - b1 * p2
    val x2 = -a2 * p1 + a1 * p2

    val abs = a1 * b2 - a2 * b1

    val s1 = x1 / abs
    val s2 = x2 / abs
    if (abs * s1 != x1 || abs * s2 != x2) return null to null
    return s1 to s2
}

fun solve(a: Pair<Long, Long>, b: Pair<Long, Long>, target: Pair<Long, Long>): Long? {
    val (s1, s2) = solve(a.first, a.second, b.first, b.second, target.first, target.second)
    if (s1 == null || s2 == null) return null
    return s1 * 3L + s2 * 1L
}

fun read(): Triple<MutableList<Pair<Long, Long>>, MutableList<Pair<Long, Long>>, MutableList<Pair<Long, Long>>> {
    val file = openFile("tasks/t13/input.txt")
    val lines = file.readLines()

    val buttonA = mutableListOf<Pair<Long, Long>>()
    val buttonB = mutableListOf<Pair<Long, Long>>()
    val prize = mutableListOf<Pair<Long, Long>>()

    fun parse(line: String, pattern: String): Pair<Long, Long> {
        val match = pattern.toRegex().find(line)
        val first = match!!.groupValues[1].toLong()
        val second = match.groupValues[2].toLong()
        return first to second
    }

    for (line in lines) {
        if (line.startsWith("Button A:")) {
            val x = parse(line, "X\\+(\\d+), Y\\+(\\d+)")
            buttonA.add(x)
        } else if (line.startsWith("Button B:")) {
            val x = parse(line, "X\\+(\\d+), Y\\+(\\d+)")
            buttonB.add(x)
        } else if (line.startsWith("Prize:")) {
            val x = parse(line, "X=(\\d+), Y=(\\d+)")
            prize.add(x)
        }
    }

    return Triple(buttonA, buttonB, prize)
}