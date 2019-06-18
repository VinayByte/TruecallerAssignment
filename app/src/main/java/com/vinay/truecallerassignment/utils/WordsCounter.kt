package com.vinay.truecallerassignment.utils

import java.io.InputStream


/**
 * Created by VINAY on 2019-06-18.
 * vinay6kr@gmail.com
 */
class WordsCounter {
    companion object {
        fun getWordsOccurrenceCount(string: String): Map<String, Int> {
            if (string.isEmpty()) return mapOf()
            val words = string.split("\\s".toRegex())
            return words.groupingBy { it.toLowerCase() }.eachCount()
        }

        fun getWordsOccurrenceCount(inputStream: InputStream): Map<String, Int> {
            val string = inputStream.bufferedReader().use { it.readText() }
            return getWordsOccurrenceCount(string)
        }
    }
}