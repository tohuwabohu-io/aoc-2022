package io.tohuwabohu.aoc2022.day11

import io.tohuwabohu.aoc2022.util.productOf

fun part01(): Long {
    return calculateWorryLevel(20, 3)
}

fun part02(): Long {
    return calculateWorryLevel(10000, 1)
}

private fun calculateWorryLevel(rounds: Int, divisor: Int): Long {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc11.txt")

    val monkeys = reader.lineSequence()
        .chunked(7)
        .map { chunk -> parseMonkey(chunk) }
        .associateBy { it.number }
        .toMap()

    val lcmPrime = monkeys.values.productOf { monkey -> monkey.divisor }

    for (round in 1..rounds) {
        monkeys.values.forEach { monkey ->
            val itemIterator = monkey.items.iterator()

            while (itemIterator.hasNext()) {
                var worryLevel = monkey.operation(itemIterator.next()) / divisor
                worryLevel %= lcmPrime

                val passTo = monkey.test(worryLevel)
                monkeys[passTo]!!.items.add(worryLevel)

                itemIterator.remove()

                monkey.inspections++
            }
        }
    }

    return monkeys.values.sortedByDescending { it.inspections }.take(2).productOf { it.inspections }
}

private fun parseMonkey(lines: List<String>): Monkey {
    val divisor = lines[3].substringAfter("Test: divisible by ").toLong()
    val operations = lines[2].substringAfter("Operation: new = old ").split(" ")
    val trueMonkey = lines[4].substringAfter("If true: ").split(" ")[3].trim().toInt()
    val falseMonkey = lines[5].substringAfter("If false: ").split(" ")[3].trim().toInt()

    fun test(worryLevel: Long): Int {
        return if (worryLevel % divisor == 0L) trueMonkey else falseMonkey
    }

    fun operation(worryLevel: Long): Long {
        val increment = if (operations[1] == "old") worryLevel else operations[1].toLong()

        return when (operations[0]) {
            "+" -> worryLevel + increment
            "*" -> worryLevel * increment
            else -> worryLevel
        }
    }

    return Monkey(
        number = lines[0].split(" ")[1].substringBefore(":").toInt(),
        items = lines[1].substringAfter("Starting items:").split(", ").map { it.trim().toLong() }.toMutableList(),
        operation = ::operation,
        divisor = divisor,
        test = ::test,
        inspections = 0
    )
}

class Monkey(
    val number: Int,
    val items: MutableList<Long>,
    val operation: (worryLevel: Long) -> Long,
    val divisor: Long,
    val test: (worryLevel: Long) -> Int,
    var inspections: Long
)