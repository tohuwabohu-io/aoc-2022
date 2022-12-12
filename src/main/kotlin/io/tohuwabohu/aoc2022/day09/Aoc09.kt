package io.tohuwabohu.aoc2022.day09

import io.tohuwabohu.aoc2022.util.Point

fun part01(): Int {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc09.txt")

    val head = Point(1, 1)
    var tail = Point(1, 1)

    val trail = mutableSetOf<Point>()
    trail.add(Point(1, 1))

    reader.lineSequence().forEach { line ->
        val command = parseCommand(line)

        val moves = move(head, command)

        moves.asSequence().forEach { move ->
            head.x = move.x
            head.y = move.y

            if (!head.isAdjacent(tail)) {
                tail = catchUp(head, tail)

                trail.add(Point(tail.x, tail.y))
            }
        }
    }

    return trail.size
}

fun part02(): Int {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc09.txt")

    val head = Point(1, 1)
    var tails = List(9){Point(1, 1)}.toMutableList()

    val trail = mutableSetOf<Point>()
    trail.add(Point(1, 1))

    reader.lineSequence().forEach { line ->
        val command = parseCommand(line)

        val moves = move(head, command)

        moves.asSequence().forEach { move ->
            head.x = move.x
            head.y = move.y

            var previous = head

            tails.withIndex().forEach{ segment ->
                if (!previous.isAdjacent(segment.value)) {
                    val tmp = catchUp(previous, segment.value)

                    tails[segment.index].x = tmp.x
                    tails[segment.index].y = tmp.y
                }

                if (segment.index == tails.size - 1) {
                    trail.add(Point(segment.value.x, segment.value.y))
                }

                previous = segment.value
            }
        }
    }

    return trail.size
}

private fun move(from: Point, command: Command): List<Point> {
    val moves = mutableListOf<Point>()
    val moveset = listOf(1..command.tiles).flatten()

    when (command.direction) {
        'U' -> moveset.forEach { y -> moves.add(Point(from.x, y = from.y + y)) }
        'D' -> moveset.forEach { y -> moves.add(Point(from.x, y = from.y - y)) }
        'R' -> moveset.forEach { x -> moves.add(Point(x = from.x + x, from.y)) }
        'L' -> moveset.forEach { x -> moves.add(Point(x = from.x - x, from.y)) }
    }

    return moves
}

private fun catchUp(head: Point, tail: Point): Point {
    if (tail.x < head.x) tail.x += 1

    if (tail.x > head.x) tail.x -= 1

    if (tail.y < head.y) tail.y += 1

    if (tail.y > head.y) tail.y -= 1

    return Point(tail.x, tail.y)
}

private fun parseCommand(line: String): Command {
    val split = line.split(" ")

    return Command(direction = split[0][0], tiles = split[1].toInt())
}

private class Command(val direction: Char, val tiles: Int)