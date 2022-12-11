package io.tohuwabohu.aoc2022.util

import java.io.BufferedReader
import java.io.File

fun getBufferedReader(fileName: String): BufferedReader
        = File(fileName).bufferedReader()

inline fun <T> Iterable<T>.multiplyInt(selector: (T) -> Int): Int {
    this.firstOrNull() ?: return 0
    var result = 1
    for (element in this) {
        result *= selector(element)
    }
    return result
}