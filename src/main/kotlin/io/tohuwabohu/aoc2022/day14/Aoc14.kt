package io.tohuwabohu.aoc2022.day14

import io.tohuwabohu.aoc2022.util.Point

private val adjacent = listOf(
    Point(0, 1), // straight down
    Point(-1, 1), // down left
    Point(1, 1) // down right
)

fun part01(): Int {
    val scanResult = scan(parseList(), infiniteFloor = false)
    val matrix = scanResult.second
    val minX = scanResult.first

    val tracker = Tracker(0, false)

    trickle(matrix, 0, 500 - minX, tracker)

    return tracker.counter
}

fun part02(): Int {
    val scanResult = scan(parseList(), infiniteFloor = true)
    val matrix = scanResult.second
    val minX = scanResult.first

    val tracker = Tracker(0, false)

    trickle(matrix, 0, 500 - minX, tracker)

    return tracker.counter
}

private fun parseList(): List<List<Tile>> {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc14.txt")

    return reader.lineSequence().map { line -> line.split(" -> ") }.map { point ->
        point.map { chunk ->
            val parts = chunk.split(",")
            Tile(x = parts[0].toInt(), y = parts[1].toInt(), reservoir = '.')
        }.toList()
    }.toList()
}

private fun scan(tiles: List<List<Tile>>, infiniteFloor: Boolean): Pair<Int, Array<Array<Tile>>> {
    val flattened = tiles.flatten()

    val minX = flattened.minOf { tile -> tile.x }
    val maxX = flattened.maxOf { tile -> tile.x }
    val maxY = flattened.maxOf { tile -> tile.y }

    val sizeX = if (!infiniteFloor) maxX - minX else maxY * 3 // this is totally not a guesstimation
    val offsetX = if (!infiniteFloor) minX else minX - maxY

    var matrix = Array(maxY + 1) { Array(sizeX + 1) { Tile(0, 0, '.') } }

    tiles.forEach { commandLine ->
        rockOn(matrix, commandLine, offsetX)
    }

    if (infiniteFloor) {
        matrix = matrix.plus(Array(matrix[0].size) { Tile(0, matrix.size, '.') })
        matrix = matrix.plus(Array(matrix[0].size) { Tile(0, matrix.size + 1, '#') })
    }

    return offsetX to matrix
}

private fun rockOn(matrix: Array<Array<Tile>>, tiles: List<Tile>, offsetX: Int) {
    tiles.zipWithNext().forEach { (current, next) ->
        val xInterpolated = current.x - offsetX

        val yRange = if (current.y > next.y) next.y..current.y else current.y..next.y
        val xRange = if (current.x > next.x) next.x - offsetX..xInterpolated else xInterpolated..next.x - offsetX

        for (y in yRange) {
            matrix[y][xInterpolated] = Tile(xInterpolated, y, '#')
        }

        for(x in xRange) {
            matrix[current.y][x] = Tile(x, current.y, '#')
        }
    }
}

private fun trickle(matrix: Array<Array<Tile>>, startY: Int, startX: Int, tracker: Tracker): Boolean {
    if (tracker.overflow) return true

    adjacent.forEach { point ->
        val search = Point(startX + point.x, startY + point.y)

        if (search.y in matrix.indices && search.x in matrix[0].indices) {
            if (matrix[search.y][search.x].reservoir == '.') {
                trickle(matrix, search.y, search.x, tracker)
            }
        } else {
            tracker.overflow = true
        }
    }

    if (!tracker.overflow) {
        tracker.counter++
        matrix[startY][startX].reservoir = 'o'
    }

    return true
}

private class Tile(x: Int, y: Int, var reservoir: Char = '.') : Point(x,y) {
    override fun toString(): String {
        return "$reservoir"
    }
}

private class Tracker(var counter: Int, var overflow: Boolean)