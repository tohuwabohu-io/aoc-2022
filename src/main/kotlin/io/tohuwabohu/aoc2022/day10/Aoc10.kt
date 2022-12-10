package io.tohuwabohu.aoc2022.day10

fun part01(): Int {
    var register = 1
    var signalStrength = 0

    parseInstructions().forEachIndexed { i, cycleValue ->
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

    parseInstructions().forEach { cycleValue ->
        if (index in spritePosition - 1..spritePosition + 1) {
            print("#")
        } else {
            print(".")
        }

        spritePosition += cycleValue

        if ((index + 1) == screenWidth) {
            println()
            index = 0
        } else index++
    }
}

private fun parseInstructions(): Sequence<Int> {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc10.txt")

    return reader.lineSequence()
        .map { line -> line.split(" ") }
        .mapIndexed { index, parts -> // first cycle has one extra step
            if (parts.size > 1) {
                Cycle(value = parts[1].toInt(), ttc = if (index == 0) 2 + 1 else 2)
            } else {
                Cycle(value = 0, ttc = if (index == 0) 1 + 1 else 1)
            }
        }
        .zipWithNext()
        .flatMap { pair -> if (pair.first.ttc == 2) listOf(pair.first.value, 0) else listOf(pair.first.value) }
}

private class Cycle(val value: Int, val ttc: Int)