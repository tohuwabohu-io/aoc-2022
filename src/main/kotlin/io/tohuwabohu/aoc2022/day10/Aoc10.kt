package io.tohuwabohu.aoc2022.day10

fun part01(): Int {
    var register = 1
    var signalStrength = 0

    getCycles().forEachIndexed { i, cycleValue ->
        val index = i + 1

        if (index == 20 || (index > 20 && ((index - 20) % 40) == 0)) {
            signalStrength += register * index
        }

        register += cycleValue
    }

    return signalStrength
}

fun part02() {
    var spritePosition = 1

    val screenWidth = 40
    var index = 0

    getCycles().forEach{ cycleValue ->
        if (index in spritePosition - 1 .. spritePosition + 1) {
            print("#")
        } else {
            print(".")
        }

        spritePosition += cycleValue

        if ((index + 1) == screenWidth) {
            println()
            index = 0
        } else index ++
    }
}

private fun getCycles(): MutableList<Int> {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc10.txt")

    val commands = reader.lineSequence()
        .map { line -> line.split(" ") }
        .map { parts -> if (parts.size > 1) Cycle(value = parts[1].toInt(), ttc = 2) else Cycle(value = 0, ttc = 1) }
        .toList()

    val cycles = mutableListOf<Int>()

    commands.forEachIndexed { index, cycle ->
        val previous = if (index > 0) commands[index - 1] else null

        previous?.let { c ->
            if (c.ttc == 1) {
                cycles.add(cycle.value)
            } else {
                cycles.addAll(listOf(0, cycle.value))
            }
        } ?: cycles.addAll(listOf(0, cycle.value)) // <- that's the first one
    }

    return cycles
}

private class Cycle(val value: Int, val ttc: Int)