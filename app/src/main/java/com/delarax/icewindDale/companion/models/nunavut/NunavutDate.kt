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
     * The number of holidays that have occurred so far, including the current day if it's a holiday.
     */
    @Throws(InvalidDateException::class)
    fun numHolidaysPassed() : Int {
        if (!isValid) { throw InvalidDateException(this) }

        // find the last holiday that occurred within the SAME year (null if there are none)
        val lastHoliday = holiday ?: season!!.lastHoliday(year).takeIf { lastHoliday ->
            val finalHolidayOfTheYear = NunavutHoliday.values().last()
            lastHoliday.ordinal != finalHolidayOfTheYear.ordinal ||
                    season.ordinal >= finalHolidayOfTheYear.nextSeason.ordinal
        }

        return lastHoliday?.let {
            NunavutHoliday.values()
                .slice(0..it.ordinal)
                .filter { holiday -> isLeapYear || !holiday.isQuadrennial }
                .count()
        } ?: 0
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
        } ?: holiday!!.priorSeason.numDaysInSeasons() + numHolidaysPassed()
    }
    
    companion object {
        val maxDaysInYear: Int = NunavutSeason.values().last().numDaysInSeasons() +
                NunavutHoliday.values().size

        @Throws(InvalidDateException::class)
        fun midwinter(year: Int) : NunavutDate = getHoliday(NunavutHoliday.MIDWINTER, year)
        @Throws(InvalidDateException::class)
        fun omingmak(year: Int) : NunavutDate = getHoliday(NunavutHoliday.OMINGMAK, year)
        @Throws(InvalidDateException::class)
        fun sunFestival(year: Int) : NunavutDate = getHoliday(NunavutHoliday.SUN_FESTIVAL, year)
        @Throws(InvalidDateException::class)
        fun alianat(year: Int) : NunavutDate = getHoliday(NunavutHoliday.ALIANAT, year)
        @Throws(InvalidDateException::class)
        fun tunniqaijuk(year: Int) : NunavutDate = getHoliday(NunavutHoliday.TUNNIQAIJUK, year)
        @Throws(InvalidDateException::class)
        fun moonFeast(year: Int) : NunavutDate = getHoliday(NunavutHoliday.MOON_FEAST, year)

        @Throws(InvalidDateException::class)
        private fun getHoliday(holiday: NunavutHoliday, year: Int) : NunavutDate {
            val date = NunavutDate(day = 1, season = null, year = year, holiday = holiday)
            if (!date.isValid) { throw(InvalidDateException(date)) }
            return date
        }
    }
}