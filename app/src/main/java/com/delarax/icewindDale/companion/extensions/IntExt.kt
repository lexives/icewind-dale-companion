package com.delarax.icewindDale.companion.extensions

fun Int.isLeapYear() : Boolean = this % 4 == 0

fun Int?.toStringOrNull() : String? = this.toString().let {
    if (it == "null") null else it
}

fun Int?.toStringOrEmpty() : String = this.toString().let {
    if (it == "null") "" else it
}