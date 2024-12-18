package one.pre.common

import java.io.File
import kotlin.reflect.KClass

fun parse(str: String, type: KClass<*>): Any {
    val v = when (type) {
        Int::class -> str.toInt()
        Long::class -> str.toLong()
        Double::class -> str.toDouble()
        String::class -> str
        else -> throw UnsupportedOperationException()
    }

    return v
}

inline fun <reified T> parseArray(line: String, separator: String? = null): List<T> {
    val parts = split(line, separator)
    return parts.map { parse(it, T::class) as T }
}

inline fun <reified T1, reified T2> parsePair(line: String, separator: String? = null): Pair<T1, T2> {
    val parts = split(line, separator)
    val v1 = parse(parts[0], T1::class) as T1
    val v2 = parse(parts[1], T2::class) as T2
    return v1 to v2
}

fun split(line: String, separator: String? = null): List<String> {
    if (separator == null) {
        return line.split("\\s+".toRegex())
    }

    return line.split(separator)
}

fun openFile(path: String) = File("C:/github/adventofcode/src/main/kotlin/$path")

fun addInt(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>) = pos1.first + pos2.first to pos1.second + pos2.second
fun addLong(pos1: Pair<Long, Long>, pos2: Pair<Long, Long>) = pos1.first + pos2.first to pos1.second + pos2.second
fun sub(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>) = pos1.first - pos2.first to pos1.second - pos2.second
fun mul(pos: Pair<Int, Int>, x: Int) = pos.first * x to pos.second * x
fun div(pos: Pair<Int, Int>, x: Int) = pos.first / x to pos.second / x

typealias Point = Pair<Int, Int>

fun <T> List<List<T>>.get(point: Point) = this[point.first][point.second]!!
fun <T> List<List<T>>.print(transform: (v: T) -> String) {
    for (row in this) {
        for (v in row) {
            print(transform(v))
            print(" ")
        }
        println()
    }
    println()
}

fun <T> find(map: List<List<T>>, v: T): Point {
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (map[i][j] == v) {
                return i to j
            }
        }
    }
    return -1 to -1
}

typealias Direction = Pair<Int, Int>

val top: Direction = -1 to 0
val right: Direction = 0 to 1
val bottom: Direction = 1 to 0
val left: Direction = 0 to -1
val directions = listOf<Direction>(top, right, bottom, left)

fun Direction.turnAround() = directions[(directions.indexOf(this) + 2) % directions.size]
fun Direction.turnLeft() = directions[(directions.indexOf(this) + directions.size - 1) % directions.size]
fun Direction.turnRight() = directions[(directions.indexOf(this) + 1) % directions.size]
