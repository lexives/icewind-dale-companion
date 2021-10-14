package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday.MIDWINTER
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday.MOON_FEAST

fun HarposDate.isLeapYear() : Boolean = this.year.isLeapYear()

fun HarposDate.isValid() : Boolean {
    return !(
        (this.month == null && this.holiday == null) ||
        (this.month != null && this.holiday != null) ||
        (this.year < 1) ||
        (this.day !in (1..30)) ||
        (this.holiday != null && this.day != 1) ||
        (this.holiday == MIDWINTER && !this.isLeapYear())
    )
}

/**
 * The number of holidays that have occurred so far, including the current day if it's a holiday.
 */
@Throws(InvalidDateException::class)
fun HarposDate.numHolidaysPassed() : Int {
    if (!this.isValid()) {
        throw InvalidDateException(this)
    }

    val lastHoliday = this.month?.lastHoliday() ?: this.holiday!!
    val numYearlyHolidaysPassed =
        if (lastHoliday == MOON_FEAST && this.month == HarposMonth.HAMMER) {
            0
        } else {
            lastHoliday.ordinal
        }

    return if (this.isLeapYear() && this.month != HarposMonth.HAMMER) {
        numYearlyHolidaysPassed + 1
    } else {
        numYearlyHolidaysPassed
    }
}
