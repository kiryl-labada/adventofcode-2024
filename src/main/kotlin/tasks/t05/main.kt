package one.pre.tasks.t05

import one.pre.common.openFile
import one.pre.common.parseArray
import one.pre.common.parsePair

fun main() {
    val (rules, pageOrder) = read("tasks/t05/input.txt")
    val (result1, result2) = solve(rules, pageOrder)
    println(result1)
    println(result2)
}

fun solve(rules: List<Pair<Int, Int>>, pageOrders: List<MutableList<Int>>): Pair<Int, Int> {
    val ruleMap = mutableMapOf<Int, MutableList<Int>>()
    for (rule in rules) {
        var dependencies = ruleMap[rule.first]
        if (dependencies == null) {
            dependencies = mutableListOf()
            ruleMap[rule.first] = dependencies
        }
        dependencies.add(rule.second)
    }

    val validOrders = mutableListOf<List<Int>>()
    val invalidOrders = mutableListOf<List<Int>>()

    for (pageOrder in pageOrders) {
        val pageMap = mutableMapOf<Int, Int>()
        for (i in pageOrder.indices) {
            pageMap[pageOrder[i]] = i
        }

        fun isValidOrder(): Boolean {
            for (i in pageOrder.indices) {
                val v = pageOrder[i]
                val dependencies = ruleMap[v] ?: continue
                for (d in dependencies) {
                    val di = pageMap[d] ?: continue
                    if (i >= di)
                        return false
                }
            }

            return true
        }

        if (isValidOrder()) {
            validOrders.add(pageOrder)
            continue
        }

        invalidOrders.add(pageOrder)
        fun reorder() {
            var i = 0
            while (i < pageOrder.size) {
                val v = pageOrder[i]
                val dependencies = ruleMap[v]
                if (dependencies == null) {
                    i++
                    continue
                }

                val di = dependencies.minOf { d ->
                    val di = pageMap[d]
                    if (di != null && i >= di) {
                        return@minOf di
                    }

                    return@minOf Int.MAX_VALUE
                }

                if (di == Int.MAX_VALUE) {
                    i++
                    continue
                }

                val d = pageOrder[di]
                pageOrder[i] = pageOrder[di]
                pageOrder[di] = v
                pageMap[v] = di
                pageMap[d] = i
                i = di
            }
        }

        reorder()
    }

    val validSum = validOrders.sumOf { it[it.size / 2] }
    val invalidSum = invalidOrders.sumOf { it[it.size / 2] }

    return validSum to invalidSum
}

fun read(path: String): Pair<List<Pair<Int, Int>>, List<MutableList<Int>>> {
    val file = openFile(path)

    val lines = file.readLines()
    val emptyLineIndex = lines.indexOf("")

    val rules = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until emptyLineIndex) {
        val line = lines[i]
        rules.add(parsePair(line, "|"))
    }

    val pageOrder = mutableListOf<MutableList<Int>>()
    for (i in emptyLineIndex + 1 until lines.size) {
        val line = lines[i]
        pageOrder.add(parseArray<Int>(line, ",").toMutableList())
    }

    return rules to pageOrder
}