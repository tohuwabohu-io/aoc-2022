package io.tohuwabohu.aoc2022.day15

import io.tohuwabohu.aoc2022.util.Point
import kotlin.math.abs
import kotlin.math.min

fun part01(): Int {
    return search(scan(), searchY = 2000000)
}

private fun scan(): List<Signal> {
    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc15.txt")

    return reader.lineSequence()
        .map { line -> line.split(": ") }
        .map { (sensorInfo, beaconInfo) ->
            val sensor = parseInfo(sensorInfo)
            val beacon = parseInfo(beaconInfo)

            Signal(sensor, beacon)
        }
        .toList()
}

private fun search(signals: List<Signal>, searchY: Int): Int {
    val beacons = signals.map { signal -> signal.beacon }.toSet()
    val maxManhattanDistance = signals.maxOf { signal -> signal.distance }

    val minX = signals.minOf { signal -> min(signal.sensor.x, signal.beacon.x) } - maxManhattanDistance
    val maxX = signals.maxOf { signal -> min(signal.sensor.x, signal.beacon.x) } + maxManhattanDistance

    var s = signals.fold(mutableSetOf<Point>()) { acc, signal ->
        for (x in minX ..maxX + 1) {
            val searchSignal = Signal(Point(x, searchY), signal.sensor)

            if (!beacons.contains(searchSignal.sensor) && searchSignal.distance <= signal.distance) {
                acc.add(searchSignal.sensor)
            }
        }

        acc
    }

    return s.size
}

private fun parseInfo(info: String): Point {
    val x = info.substring(info.indexOfFirst { c -> c == '=' } + 1).takeWhile { c -> c != ',' }.toInt()
    val y = info.substring(info.indexOfLast { c -> c == '=' } + 1, info.length).toInt()

    return Point(x, y)
}

private fun manhattanDistance(a: Point, b: Point): Int {
    return abs(a.x - b.x) + abs(a.y - b.y)
}

private class Signal(val sensor: Point, val beacon: Point) {
    val distance = manhattanDistance(sensor, beacon)
}