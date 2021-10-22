package com.delarax.icewindDale.companion.extensions

import kotlin.math.abs

fun Int.isLeapYear() : Boolean = this % 4 == 0

fun Int?.toStringOrNull() : String? = this.toString().let {
    if (it == "null") null else it
}

fun Int?.toStringOrEmpty() : String = this.toString().let {
    if (it == "null") "" else it
}

fun Int.leadingZeros(places: Int) : String = this.toStringOrEmpty().padStart(places, '0')

fun Int.toStringWithSuffix() : String = this.let {
    if (abs(it) % 100 !in (11..13)) {
        when (abs(it) % 10) {
            1 -> "${it}st"
            2 -> "${it}nd"
            3 -> "${it}rd"
            else -> "${it}th"
        }
    } else {
        "${it}th"
    }
}