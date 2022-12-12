package io.tohuwabohu.aoc2022.util

import java.io.BufferedReader
import java.io.File
import java.math.BigInteger

fun getBufferedReader(fileName: String): BufferedReader
        = File(fileName).bufferedReader()

inline fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long {
    this.firstOrNull() ?: return 0L
    var result = 1L
    for (element in this) {
        result *= selector(element)
    }
    return result
}

open class Point(var x: Int, var y: Int) {
    override fun toString(): String {
        return "(x $x, y $y)"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Point) this.x == other.x && this.y == other.y else super.equals(other)
    }

    fun isAdjacent(point: Point): Boolean {
        return listOf(x - 1..x + 1).flatten().contains(point.x) && listOf(y - 1..y + 1).flatten().contains(point.y)
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}
