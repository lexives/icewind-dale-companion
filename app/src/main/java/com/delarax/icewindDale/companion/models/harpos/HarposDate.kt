package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.InvalidDateException

data class HarposDate(
    val day: Int,
    val month: HarposMonth?,
    val year: Int,
    val holiday: HarposHoliday? = null
) {
    val isLeapYear: Boolean = year.isLeapYear()
    val isValid: Boolean = !(
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
    fun absoluteDayNumber(): Int {
        if (!isValid) { throw InvalidDateException(this) }

        // every month has 30 days in the Harpos calendar
        return month?.let { month ->
            month.ordinal * 30 + day + numHolidaysPassed()
        } ?: holiday!!.absoluteDayNumber(year)
    }

    companion object {
        val maxDaysInYear: Int = HarposMonth.values().size * 30 + HarposHoliday.values().size

        @Throws(InvalidDateException::class)
        fun midwinter(year: Int) : HarposDate = getHoliday(HarposHoliday.MIDWINTER, year)
        @Throws(InvalidDateException::class)
        fun shieldmeet(year: Int) : HarposDate = getHoliday(HarposHoliday.SHIELDMEET, year)
        @Throws(InvalidDateException::class)
        fun greengrass(year: Int) : HarposDate = getHoliday(HarposHoliday.GREENGRASS, year)
        @Throws(InvalidDateException::class)
        fun midsummer(year: Int) : HarposDate = getHoliday(HarposHoliday.MIDSUMMER, year)
        @Throws(InvalidDateException::class)
        fun highharvesttide(year: Int) : HarposDate =
            getHoliday(HarposHoliday.HIGHHARVESTTIDE, year)
        @Throws(InvalidDateException::class)
        fun moonFeast(year: Int) : HarposDate = getHoliday(HarposHoliday.MOON_FEAST, year)

        @Throws(InvalidDateException::class)
        private fun getHoliday(holiday: HarposHoliday, year: Int) : HarposDate {
            val date = HarposDate(day = 1, month = null, year = year, holiday = holiday)
            if (!date.isValid) { throw(InvalidDateException(date)) }
            return date
        }
    }
}

