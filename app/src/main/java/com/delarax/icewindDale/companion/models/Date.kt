package com.delarax.icewindDale.companion.models

interface Date {
    val isLeapYear: Boolean
    val isValid: Boolean
    fun absoluteDayNumber(): Int
}