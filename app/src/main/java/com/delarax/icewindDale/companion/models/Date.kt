package com.delarax.icewindDale.companion.models

interface Date {
    val day: Int
    val year: Int
    val isLeapYear: Boolean
    val isValid: Boolean
    fun absoluteDayNumber(): Int
}