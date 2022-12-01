package util

import java.io.BufferedReader
import java.io.File

fun getBufferedReader(fileName: String): BufferedReader
        = File(fileName).bufferedReader()