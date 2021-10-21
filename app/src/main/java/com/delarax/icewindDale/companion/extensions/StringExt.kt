package com.delarax.icewindDale.companion.extensions

fun String.capitalize(): String = this[0].uppercaseChar() + this.substring(1).lowercase()

fun String.enumCaseToTitleCase(): String = this.split("_")
    .joinToString(separator = " ") {
        it.capitalize()
    }

fun String?.toIntOrZero(): Int = this?.toIntOrNull() ?: 0

/**
 * Creates a triple of strings by splitting up the string into three sub-strings.
 *
 * @param i the index of the first character that will start the middle sub-string
 * @param len the length of the middle sub-string
 *
 * @sample
 * [splitStringIntoThree]("0123456789", 3, 2) == ("012", "34", "56789")
 * [splitStringIntoThree]("0123456789", 0, 1) == ("", "0", "123456789")
 * [splitStringIntoThree]("0123456789", 9, 1) == ("12345678", "9", "")
 */
fun String.toTriple(i: Int, len: Int) : Triple<String, String, String> = Triple(
    this.slice(0 until i),
    this.slice(i until i + len),
    this.slice(i + len until this.length)
)