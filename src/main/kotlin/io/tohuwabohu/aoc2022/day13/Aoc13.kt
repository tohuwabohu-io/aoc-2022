package io.tohuwabohu.aoc2022.day13

import kotlin.RuntimeException

fun part01(): Int {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc13.txt")

    return reader.lineSequence().chunked(3)
        .mapIndexed { index, (line1, line2) -> Triple(index, parseList(line1), parseList(line2)) }
        .sumOf { (index: Int, left: Packet, right: Packet) -> if (left <= right) index + 1 else 0 }
}

fun part02(): Int {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc13.txt")

    val divider1 = parseList("[[2]]")
    val divider2 = parseList("[[6]]")

    val list = reader.lineSequence().chunked(3)
        .fold(mutableListOf()) { acc: MutableList<Packet>, (line1, line2) ->
            acc.addAll(listOf(parseList(line1), parseList(line2)))

            acc
        }

    list.addAll(listOf(divider1, divider2))
    list.sort()

    return (list.indexOf(divider1) + 1) * (list.indexOf(divider2) + 1)
}

private fun parseList(line: String): Packet {
    val collector = mutableListOf(mutableListOf<Packet>())
    var digit = ""

    for (c in line) {
        when (c) {
            '[' -> { collector.add(mutableListOf()) }
            ',', ']' -> {
                if (digit.isNotEmpty()) {
                    collector.last().add(PacketInt(digit.toInt()))
                    digit = ""
                }

                if (c == ']') {
                    if (collector.size <= 1) break

                    val last = collector.removeLast()

                    collector.last().add(PacketList(last))
                }
            }
            else -> digit = digit.plus(c)
        }
    }

    return collector.first()[0]
}

private interface Packet : Comparable<Packet>

private class PacketInt(val value: Int): Packet {
    override fun compareTo(other: Packet): Int {
        return when (other) {
            is PacketInt -> value.compareTo(other.value)
            is PacketList -> PacketList(listOf(this)).compareTo(other)
            else -> throw RuntimeException("schas")
        }
    }
}

private class PacketList(val values: List<Packet>) : Packet {
    override fun compareTo(other: Packet): Int {
        return when (other) {
            is PacketInt -> compareTo(PacketList(listOf(other)))
            is PacketList -> {
                values.forEachIndexed { index, value ->
                    if (index >= other.values.size) return 1

                    val otherItem = other.values[index]

                    val comparison = value.compareTo(otherItem)

                    if (comparison != 0) return comparison
                }

                return values.size.compareTo(other.values.size)
            }
            else -> throw RuntimeException("schas2")
        }
    }

    override fun toString(): String {
        return "$values".replace(" ", "")
    }
}