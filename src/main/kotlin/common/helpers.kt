package one.pre.common

import java.io.File
import kotlin.reflect.KClass

val directions = listOf(
    0 to 1,
    0 to -1,
    1 to 0,
    -1 to 0
)

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
