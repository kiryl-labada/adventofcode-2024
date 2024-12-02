package one.pre.common

import java.io.File
import kotlin.reflect.KClass

fun parse(str: String, type: KClass<*>): Any {
    val v = when (type) {
        Int::class -> str.toInt()
        Double::class -> str.toDouble()
        String::class -> str
        else -> throw UnsupportedOperationException()
    }

    return v
}

inline fun <reified T> parseArray(line: String): List<T> {
    val parts = split(line)
    return parts.map { parse(it, T::class) as T }
}

inline fun <reified T1, reified T2> parsePair(line: String): Pair<T1, T2> {
    val parts = split(line)
    val v1 = parse(parts[0], T1::class) as T1
    val v2 = parse(parts[1], T2::class) as T2
    return v1 to v2
}

fun split(line: String) = line.split("\\s+".toRegex())

fun openFile(path: String) = File("C:/github/adventofcode/src/main/kotlin/$path")