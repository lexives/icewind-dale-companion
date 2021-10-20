package com.delarax.icewindDale.companion.extensions

fun String.capitalize() = this[0].uppercaseChar() + this.substring(1).lowercase()

fun String.enumCaseToTitleCase() = this.split("_").joinToString(separator = " ") { it.capitalize() }