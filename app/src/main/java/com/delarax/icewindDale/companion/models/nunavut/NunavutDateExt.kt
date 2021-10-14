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

@Throws(InvalidDateException::class)
fun NunavutDate.priorSeason() : NunavutSeason {
    if (!this.isValid()) {
        throw InvalidDateException(this)
    }
    return this.season?.priorSeason() ?: this.holiday!!.priorSeason
}

@Throws(InvalidDateException::class)
fun NunavutDate.nextSeason() : NunavutSeason {
    if (!this.isValid()) {
        throw InvalidDateException(this)
    }
    return this.season?.nextSeason() ?: this.holiday!!.nextSeason
}

/**
 * The last holiday that occurred, including today if it's a holiday
 */
@Throws(InvalidDateException::class)
fun NunavutDate.lastHoliday() : NunavutHoliday {
    if (!this.isValid()) {
        throw InvalidDateException(this)
    }
    return this.season?.lastHoliday(this.year) ?: this.holiday!!
}

/**
 * The next holiday that will occur, not including today if it's a holiday
 */
@Throws(InvalidDateException::class)
fun NunavutDate.nextHoliday() : NunavutHoliday {
    if (!this.isValid()) {
        throw InvalidDateException(this)
    }
    return this.season?.nextHoliday(this.year) ?: this.holiday!!.nextSeason.let { nextSeason ->
        if (nextSeason == NunavutSeason.DENNING_POLAR_BEAR) {
            nextSeason.nextHoliday(this.year + 1)
        } else {
            nextSeason.nextHoliday(this.year)
        }
    }
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

