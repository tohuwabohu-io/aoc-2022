package io.tohuwabohu.aoc2022.day12

import io.tohuwabohu.aoc2022.util.Point

val adjacentOffset = setOf(
    Point(1, 0),
    Point(-1, 0),
    Point(0, 1),
    Point(0, -1)
)

fun part01(): Int {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc12.txt")
    var map = arrayOf<CharArray>()

    var start: Point? = null
    var end: Point? = null

    reader.lineSequence().forEachIndexed { index, line ->
        map = map.plus(line.toCharArray())

        if (start == null) {
            val startX = line.indexOfFirst { c -> c == 'S' }
            if (startX > -1) start = Point(startX, index)
        }

        if (end == null) {
            val endX = line.indexOfFirst { c -> c == 'E' }
            if (endX > -1) end = Point(endX, index)
        }
    }

    return breadthFirstSearch(map, start!!, end!!)
}

fun part02(): Int {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc12.txt")

    var map = arrayOf<CharArray>()

    val startingPoints = mutableListOf<Point>()
    var end: Point? = null

    reader.lineSequence().forEachIndexed { index, line ->
        map = map.plus(line.toCharArray())

        line.forEachIndexed{ x, c -> if (c == 'a' || c == 'S') startingPoints.add(Point(x, index)) else if(end == null && c == 'E') end = Point(x, index) }
    }

    return startingPoints.indices.minOf{ index -> breadthFirstSearch(map, startingPoints[index], end!!) }
}

private fun breadthFirstSearch(map: Array<CharArray>, start: Point, end: Point): Int {
    val queue = mutableListOf<Point>()
    val visitedNodes = mutableSetOf<Point>()
    queue.add(start)

    val edge = Array(map.size){ IntArray(map[0].size) { map.size * map[0].size } }
    edge[start.y][start.x] = 0

    var currentNode: Point?

    while(queue.isNotEmpty()) {
        currentNode = queue.removeFirst()

        adjacentOffset.map { offset -> Point(offset.x + currentNode.x, offset.y + currentNode.y) }
            .forEach { point ->
                if (point.x !in map[0].indices || point.y !in map.indices) return@forEach

                val elevation = map[point.y][point.x]
                val difference =
                    if (currentNode == start) elevation - 'a' else if (currentNode == end) elevation - 'z' else elevation - map[currentNode.y][currentNode.x]

                if (difference <= 1) {
                    edge[point.y][point.x] = minOf(edge[point.y][point.x], edge[currentNode.y][currentNode.x] + 1)

                    if (point !in visitedNodes && point !in queue) queue.add(point)
                }
            }

        visitedNodes.add(currentNode)
    }

    return edge[end.y][end.x]
}
