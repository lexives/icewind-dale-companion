package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.InvalidDateException

data class NunavutDate(
    val day: Int,
    val season: NunavutSeason?,
    val year: Int,
    val holiday: NunavutHoliday? = null
) {
    val isLeapYear: Boolean = year.isLeapYear()
    val isValid: Boolean = !(
        (season == null && holiday == null) ||
        (season != null && holiday != null) ||
        (year < 1) ||
        (season != null && day !in (1..season.numDays)) ||
        (holiday != null && day != 1) ||
        (holiday?.isQuadrennial == true && !isLeapYear)
    )

    @Throws(InvalidDateException::class)
    fun priorSeason() : NunavutSeason {
        if (!isValid) { throw InvalidDateException(this) }
        return season?.priorSeason() ?: holiday!!.priorSeason
    }

    @Throws(InvalidDateException::class)
    fun nextSeason() : NunavutSeason {
        if (!isValid) { throw InvalidDateException(this) }
        return season?.nextSeason() ?: holiday!!.nextSeason
    }

    /**
     * The last holiday that occurred, including today if it's a holiday
     */
    @Throws(InvalidDateException::class)
    fun lastHoliday() : NunavutHoliday {
        if (!isValid) { throw InvalidDateException(this) }
        return season?.lastHoliday(year) ?: holiday!!
    }

    /**
     * The next holiday that will occur, not including today if it's a holiday
     */
    @Throws(InvalidDateException::class)
    fun nextHoliday() : NunavutHoliday {
        if (!isValid) { throw InvalidDateException(this) }
        return season?.nextHoliday(year) ?: holiday!!.nextHoliday(year)
    }

    /**
     * The number of holidays that have occurred so far,
     * including the current day if it's a holiday.
     */
    @Throws(InvalidDateException::class)
    fun numHolidaysPassed() : Int {
        if (!isValid) { throw InvalidDateException(this) }
        return season?.numHolidaysPassed(year) ?: holiday!!.numHolidaysPassed(year)
    }

    @Throws(InvalidDateException::class)
    fun absoluteDayNumber(): Int {
        if (!isValid) { throw InvalidDateException(this) }

        return season?.let { season ->
            if (season == NunavutSeason.values().first()) {
                day + numHolidaysPassed()
            } else {
                season.priorSeason().numDaysInSeasons() + day + numHolidaysPassed()
            }
        } ?: holiday!!.absoluteDayNumber(year)
    }
    
    companion object {
        val maxDaysInYear: Int = NunavutSeason.values().last().numDaysInSeasons() +
                NunavutHoliday.values().size
    }
}