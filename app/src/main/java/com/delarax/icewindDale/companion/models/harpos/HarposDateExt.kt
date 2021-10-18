package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.InvalidDateException

fun HarposDate.isLeapYear() : Boolean = this.year.isLeapYear()

fun HarposDate.isValid() : Boolean {
    return !(
        (this.month == null && this.holiday == null) ||
        (this.month != null && this.holiday != null) ||
        (this.year < 1) ||
        (this.day !in (1..30)) ||
        (this.holiday != null && this.day != 1) ||
        (this.holiday?.isQuadrennial == true && !this.isLeapYear())
    )
}

@Throws(InvalidDateException::class)
fun HarposDate.priorMonth() : HarposMonth {
    if (!this.isValid()) { throw InvalidDateException(this) }
    return this.month?.priorMonth() ?: this.holiday!!.priorMonth
}

@Throws(InvalidDateException::class)
fun HarposDate.nextMonth() : HarposMonth {
    if (!this.isValid()) { throw InvalidDateException(this) }
    return this.month?.nextMonth() ?: this.holiday!!.nextMonth
}

/**
 * The last holiday that occurred, including today if it's a holiday
 */
@Throws(InvalidDateException::class)
fun HarposDate.lastHoliday() : HarposHoliday {
    if (!this.isValid()) { throw InvalidDateException(this) }
    return this.month?.lastHoliday(this.year) ?: this.holiday!!
}

/**
 * The next holiday that will occur, not including today if it's a holiday
 */
@Throws(InvalidDateException::class)
fun HarposDate.nextHoliday() : HarposHoliday {
    if (!this.isValid()) { throw InvalidDateException(this) }
    return this.month?.nextHoliday(this.year) ?: this.holiday!!.nextHoliday(this.year)
}

/**
 * The number of holidays that have occurred so far, including the current day if it's a holiday.
 */
@Throws(InvalidDateException::class)
fun HarposDate.numHolidaysPassed() : Int {
    if (!this.isValid()) { throw InvalidDateException(this) }

    // find the last holiday that occurred within the SAME year (null if there are none)
    val lastHoliday = this.holiday ?: this.month!!.lastHoliday(this.year).takeIf { lastHoliday ->
        val finalHolidayOfTheYear = HarposHoliday.values().last()
        lastHoliday.ordinal != finalHolidayOfTheYear.ordinal ||
                this.month.ordinal >= finalHolidayOfTheYear.nextMonth.ordinal
    }

    return lastHoliday?.let {
        HarposHoliday.values()
            .slice(0..it.ordinal)
            .filter { holiday -> this.isLeapYear() || !holiday.isQuadrennial }
            .count()
    } ?: 0
}

@Throws(InvalidDateException::class)
fun HarposDate.absoluteDayNumber(): Int {
    if (!this.isValid()) { throw InvalidDateException(this) }

    // every month has 30 days in the Harpos calendar
    return this.month?.let { month ->
        month.ordinal * 30 + this.day + this.numHolidaysPassed()
    } ?: this.holiday!!.priorMonth.num() * 30 + this.numHolidaysPassed()
}
