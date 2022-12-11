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