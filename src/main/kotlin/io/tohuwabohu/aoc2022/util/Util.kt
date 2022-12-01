package io.tohuwabohu.aoc2022.util

import java.io.BufferedReader
import java.io.File

fun getBufferedReader(fileName: String): BufferedReader
        = File(fileName).bufferedReader()