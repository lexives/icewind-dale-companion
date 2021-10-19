package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.Date
import com.delarax.icewindDale.companion.exceptions.InvalidDateException

data class HarposDate(
    val day: Int,
    val month: HarposMonth?,
    val year: Int,
    val holiday: HarposHoliday? = null
) : Date {
    override val isLeapYear: Boolean = year.isLeapYear()
    override val isValid:  Boolean = !(
        (month == null && holiday == null) ||
        (month != null && holiday != null) ||
        (year < 1) ||
        (day !in (1..30)) ||
        (holiday != null && day != 1) ||
        (holiday?.isQuadrennial == true && !isLeapYear)
    )

    @Throws(InvalidDateException::class)
    fun priorMonth() : HarposMonth {
        if (!isValid) { throw InvalidDateException(this) }
        return month?.priorMonth() ?: holiday!!.priorMonth
    }

    @Throws(InvalidDateException::class)
    fun nextMonth() : HarposMonth {
        if (!isValid) { throw InvalidDateException(this) }
        return month?.nextMonth() ?: holiday!!.nextMonth
    }

    /**
     * The last holiday that occurred, including today if it's a holiday
     */
    @Throws(InvalidDateException::class)
    fun lastHoliday() : HarposHoliday {
        if (!isValid) { throw InvalidDateException(this) }
        return month?.lastHoliday(year) ?: holiday!!
    }

    /**
     * The next holiday that will occur, not including today if it's a holiday
     */
    @Throws(InvalidDateException::class)
    fun nextHoliday() : HarposHoliday {
        if (!isValid) { throw InvalidDateException(this) }
        return month?.nextHoliday(year) ?: holiday!!.nextHoliday(year)
    }

    /**
     * The number of holidays that have occurred so far,
     * including the current day if it's a holiday.
     */
    @Throws(InvalidDateException::class)
    fun numHolidaysPassed() : Int {
        if (!isValid) { throw InvalidDateException(this) }
        return month?.numHolidaysPassed(year) ?: holiday!!.numHolidaysPassed(year)
    }

    @Throws(InvalidDateException::class)
    override fun absoluteDayNumber(): Int {
        if (!isValid) { throw InvalidDateException(this) }

        // every month has 30 days in the Harpos calendar
        return month?.let { month ->
            month.ordinal * 30 + day + numHolidaysPassed()
        } ?: holiday!!.absoluteDayNumber(year)
    }

    companion object {
        val daysInLeapYear: Int = HarposMonth.values().size * 30 +
                HarposHoliday.values().size

        val daysInNonLeapYear: Int = daysInLeapYear - HarposHoliday.values().count {
            it.isQuadrennial
        }

        @Throws(InvalidDateException::class)
        fun fromAbsoluteDayNumber(dayNum: Int, year: Int) : HarposDate {
            val maxDaysInYear = if (year.isLeapYear()) daysInLeapYear else daysInNonLeapYear
            if (dayNum !in (1..maxDaysInYear)) {
                throw InvalidDateException(
                    "$dayNum is not a valid day number for year $year in the Calendar of Harpos"
                )
            }

            val holiday = HarposHoliday.values().filter {
                !it.isQuadrennial || year.isLeapYear()
            }.find {
                it.absoluteDayNumber(year) == dayNum
            }

            return holiday?.toDate(year) ?: run {
                val month = HarposMonth.values().find {
                    it.num() * 30 + it.numHolidaysPassed(year) >= dayNum
                }
                val day = ((dayNum - month!!.numHolidaysPassed(year)) % 30).takeIf {
                    it > 0
                } ?: 30
                HarposDate(day, month, year)
            }
        }
    }
}

