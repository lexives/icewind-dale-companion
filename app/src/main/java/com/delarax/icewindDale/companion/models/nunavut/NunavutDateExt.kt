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
    val isSeasonBeforeMidwinter = when (this.season) {
        NunavutSeason.DENNING_POLAR_BEAR, NunavutSeason.FALLING_STARS -> true
        NunavutSeason.IGLOO -> !this.isLeapYear()
        else -> false
    }
    val numYearlyHolidaysPassed =
        if (lastHoliday == MOON_FEAST && isSeasonBeforeMidwinter) {
            0
        } else {
            lastHoliday.ordinal
        }

    return if (this.isLeapYear() && !isSeasonBeforeMidwinter) {
        numYearlyHolidaysPassed + 1
    } else {
        numYearlyHolidaysPassed
    }
}

@Throws(InvalidDateException::class)
fun NunavutDate.absoluteDayNumber(): Int {
    if (!this.isValid()) { throw InvalidDateException(this) }

    return this.season?.let { season ->
        if (season == NunavutSeason.values()[0]) {
            this.day + this.numHolidaysPassed()
        } else {
            numDaysInSeasons(season.priorSeason()) + this.day + this.numHolidaysPassed()
        }
    } ?: numDaysInSeasons(this.holiday!!.priorSeason) + this.numHolidaysPassed()
}

/**
 * Returns the sum of all the days in the seasons up to and including the given season.
 * Does not include holidays.
 *
 * @sample
 * DENNING_POLAR_BEAR has 20 days, FALLING_STARS has 30 days,
 * so numDaysInSeasons(FALLING_STARS) == 50
 */
private fun numDaysInSeasons(season: NunavutSeason) : Int {
    var numDays = 0
    for (i in (0..season.ordinal)) {
        numDays += NunavutSeason.values()[i].numDays
    }
    return numDays
}
