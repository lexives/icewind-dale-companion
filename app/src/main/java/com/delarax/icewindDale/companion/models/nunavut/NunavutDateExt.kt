package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.MIDWINTER
import com.delarax.icewindDale.companion.models.nunavut.NunavutHoliday.MOON_FEAST

fun NunavutDate.isLeapYear() : Boolean = this.year.isLeapYear()

fun NunavutDate.isValid() : Boolean {
    return !(
        (this.season == null && this.holiday == null) ||
        (this.season != null && this.holiday != null) ||
        (this.year < 1) ||
        (this.season != null && this.day !in (1..this.season.numDays)) ||
        (this.holiday != null && this.day != 1) ||
        (this.holiday == MIDWINTER && !this.isLeapYear())
    )
}

/**
 * The number of holidays that have occurred so far, including the current day if it's a holiday.
 */
@Throws(InvalidDateException::class)
fun NunavutDate.numHolidaysPassed() : Int {
    if (!this.isValid()) {
        throw InvalidDateException(this)
    }

    val lastHoliday = this.season?.lastHoliday(this.year) ?: this.holiday!!
    val isSeasonBeforeAnyHolidays = when (this.season) {
        NunavutSeason.DENNING_POLAR_BEAR, NunavutSeason.FALLING_STARS -> true
        NunavutSeason.IGLOO -> !this.isLeapYear()
        else -> false
    }
    val numYearlyHolidaysPassed =
        if (lastHoliday == MOON_FEAST && isSeasonBeforeAnyHolidays) {
            0
        } else {
            lastHoliday.ordinal
        }

    return if (this.isLeapYear() && !isSeasonBeforeAnyHolidays) {
        numYearlyHolidaysPassed + 1
    } else {
        numYearlyHolidaysPassed
    }
}

