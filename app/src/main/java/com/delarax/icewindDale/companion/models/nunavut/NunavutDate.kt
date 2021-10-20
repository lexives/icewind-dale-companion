package com.delarax.icewindDale.companion.models.nunavut

import com.delarax.icewindDale.companion.extensions.isLeapYear
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.exceptions.InvalidDateException
import com.delarax.icewindDale.companion.extensions.leadingZeros
import com.delarax.icewindDale.companion.extensions.toStringWithSuffix
import com.delarax.icewindDale.companion.models.DateFormat
import com.delarax.icewindDale.companion.models.NunavutDateFormat

data class NunavutDate(
    val day: Int,
    val season: NunavutSeason?,
    val year: Int,
    val holiday: NunavutHoliday? = null
): Date {
    override val isLeapYear: Boolean = year.isLeapYear()
    override val isValid: Boolean = !(
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
    override fun absoluteDayNumber(): Int {
        if (!isValid) { throw InvalidDateException(this) }

        return season?.let { season ->
            if (season == NunavutSeason.values().first()) {
                day + numHolidaysPassed()
            } else {
                season.priorSeason().numDaysInSeasons() + day + numHolidaysPassed()
            }
        } ?: holiday!!.absoluteDayNumber(year)
    }

    // TODO: delegate formatting to some other class
    override fun toString(format: DateFormat): String {
        return if (isValid) {
            (format as? NunavutDateFormat)?.let { harposFormat ->
                season?.let { season ->
                    when (harposFormat) {
                        NunavutDateFormat.SHORT,
                        NunavutDateFormat.STANDARD ->
                            "$day.${season.num()}.$year"
                        NunavutDateFormat.SHORT_ALTERNATE ->
                            "$day.${season.abbreviation}.$year"
                        NunavutDateFormat.SHORT_FULL_NUMBERS,
                        NunavutDateFormat.STANDARD_FULL_NUMBERS ->
                            "${day.leadingZeros(2)}.${season.num().leadingZeros(2)}.$year"
                        NunavutDateFormat.SHORT_ALTERNATE_FULL_NUMBERS ->
                            "${day.leadingZeros(2)}.${season.abbreviation}.$year"
                        NunavutDateFormat.WRITTEN ->
                            "The ${day.toStringWithSuffix()} Day of the ${season.fullName}, " +
                                    "Year $year"
                    }
                } ?: holiday!!.let { holiday ->
                    when (harposFormat) {
                        NunavutDateFormat.SHORT,
                        NunavutDateFormat.SHORT_ALTERNATE,
                        NunavutDateFormat.SHORT_FULL_NUMBERS,
                        NunavutDateFormat.SHORT_ALTERNATE_FULL_NUMBERS ->
                            "${holiday.abbreviation}.$year"
                        NunavutDateFormat.STANDARD,
                        NunavutDateFormat.STANDARD_FULL_NUMBERS ->
                            "${holiday.fullName}, $year"
                        NunavutDateFormat.WRITTEN ->
                            "${holiday.fullName}, Year $year"
                    }
                }
            } ?: this.toString() // TODO : throw error?
        } else {
            this.toString()
        }
    }
    
    companion object {
        val daysInLeapYear: Int = NunavutSeason.values().last().numDaysInSeasons() +
                NunavutHoliday.values().size

        val daysInNonLeapYear: Int = daysInLeapYear - NunavutHoliday.values().count {
            it.isQuadrennial
        }

        @Throws(InvalidDateException::class)
        fun fromAbsoluteDayNumber(dayNum: Int, year: Int) : NunavutDate {
            val maxDaysInYear = if (year.isLeapYear()) daysInLeapYear else daysInNonLeapYear
            if (dayNum !in (1..maxDaysInYear)) {
                throw InvalidDateException(
                    "$dayNum is not a valid day number for year $year in the Nunavut Calendar"
                )
            }

            val holiday = NunavutHoliday.values().filter {
                !it.isQuadrennial || year.isLeapYear()
            }.find {
                it.absoluteDayNumber(year) == dayNum
            }

            return holiday?.toDate(year) ?: run {
                val season = NunavutSeason.values().find {
                    it.numDaysInSeasons() + it.numHolidaysPassed(year) >= dayNum
                }
                val priorSeason = season!!.priorSeason().takeIf {
                    it.ordinal < season.ordinal
                }
                val day = dayNum - season.numHolidaysPassed(year) -
                        (priorSeason?.numDaysInSeasons() ?: 0)
                NunavutDate(day, season, year)
            }
        }
    }
}