package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.InvalidDateException

fun NunavutDate.isLeapYear() : Boolean = this.year.isLeapYear()

fun NunavutDate.isValid() : Boolean {
    return !(
        (this.season == null && this.holiday == null) ||
        (this.season != null && this.holiday != null) ||
        (this.year < 1) ||
        (this.season != null && this.day !in (1..this.season.numDays)) ||
        (this.holiday != null && this.day != 1) ||
        (this.holiday?.isQuadrennial == true && !this.isLeapYear())
    )
}

@Throws(InvalidDateException::class)
fun NunavutDate.priorSeason() : NunavutSeason {
    if (!this.isValid()) { throw InvalidDateException(this) }
    return this.season?.priorSeason() ?: this.holiday!!.priorSeason
}

@Throws(InvalidDateException::class)
fun NunavutDate.nextSeason() : NunavutSeason {
    if (!this.isValid()) { throw InvalidDateException(this) }
    return this.season?.nextSeason() ?: this.holiday!!.nextSeason
}

/**
 * The last holiday that occurred, including today if it's a holiday
 */
@Throws(InvalidDateException::class)
fun NunavutDate.lastHoliday() : NunavutHoliday {
    if (!this.isValid()) { throw InvalidDateException(this) }
    return this.season?.lastHoliday(this.year) ?: this.holiday!!
}

/**
 * The next holiday that will occur, not including today if it's a holiday
 */
@Throws(InvalidDateException::class)
fun NunavutDate.nextHoliday() : NunavutHoliday {
    if (!this.isValid()) { throw InvalidDateException(this) }

    return this.season?.nextHoliday(this.year) ?: this.holiday!!.nextSeason.let { nextSeason ->
        if (nextSeason.ordinal == 0) {
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
    if (!this.isValid()) { throw InvalidDateException(this) }

    val lastHoliday = this.holiday ?: this.season!!.lastHoliday(this.year).takeIf { lastHoliday ->
        val finalHolidayOfTheYear = NunavutHoliday.values().last()
        lastHoliday.ordinal != finalHolidayOfTheYear.ordinal ||
                this.season.ordinal >= finalHolidayOfTheYear.nextSeason.ordinal
    }

    return lastHoliday?.let {
        NunavutHoliday.values()
            .slice(0..it.ordinal)
            .filter { holiday -> this.isLeapYear() || !holiday.isQuadrennial }
            .count()
    } ?: 0
}

@Throws(InvalidDateException::class)
fun NunavutDate.absoluteDayNumber(): Int {
    if (!this.isValid()) { throw InvalidDateException(this) }

    return this.season?.let { season ->
        if (season == NunavutSeason.values().first()) {
            this.day + this.numHolidaysPassed()
        } else {
            season.priorSeason().numDaysInSeasons() + this.day + this.numHolidaysPassed()
        }
    } ?: this.holiday!!.priorSeason.numDaysInSeasons() + this.numHolidaysPassed()
}