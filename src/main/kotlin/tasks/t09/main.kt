package one.pre.tasks.t09

import one.pre.common.openFile

fun main() {
    val input = read()
    val disk = solve2(input)

    var result = 0L
    for (i in disk.indices) {
        val v = disk[i]
        if (v == -1) continue

        result += i * v
    }

    println(result)
}

fun createDisk(input: List<Int>): MutableList<Int> {
    val diskSize = input.sum()
    val disk = MutableList(diskSize) { -1 }

    var id = 0
    var position = 0
    for (i in input.indices) {
        if (i % 2 == 0) {
            for (j in 0 until input[i]) {
                disk[position++] = id
            }

            id++
        } else {
            position += input[i]
        }
    }

    return disk
}

fun solve(input: List<Int>): MutableList<Int> {
    val disk = createDisk(input)

    var (left, right) = 0 to disk.size - 1
    while (left < right) {
        if (disk[left] != -1) {
            left++
            continue
        }

        if (disk[right] == -1) {
            right--
            continue
        }

        disk[left] = disk[right]
        disk[right] = -1
    }

    return disk
}

fun solve2(input: List<Int>): MutableList<Int> {
    val disk = createDisk(input)

    var right = disk.size - 1
    while (right >= 0) {
        // read right number
        // try to fit it
        // copy or skip

        if (disk[right] == -1) {
            right--
            continue
        }

        var len = 0
        val v = disk[right]
        while (right - len >= 0 && v == disk[right - len]) len++

        fun findNextFreeSpace(left: Int): Pair<Int, Int> {
            var left = left
            while (left < disk.size && disk[left] != -1) left++
            if (left > right) return -1 to -1

            var count = 1
            while (left + count < right && disk[left + count] == -1) count++
            return left to count
        }

        var (start, size) = 0 to 0
        do {
            findNextFreeSpace(start + size).also { start = it.first; size = it.second }
            if (len <= size) {
                for (i in 0 until len) {
                    disk[start + i] = v
                    disk[right - i] = -1
                }
                break
            }
        } while (size != -1)

        right -= len
    }

    return disk
}


fun read() = openFile("tasks/t09/input.txt")
    .readText()
    .toCharArray()
    .map { it.digitToInt() }
