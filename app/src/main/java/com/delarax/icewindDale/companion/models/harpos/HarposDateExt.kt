package com.delarax.icewindDale.companion.models.harpos

import com.delarax.icewindDale.companion.data.isLeapYear
import com.delarax.icewindDale.companion.models.InvalidDateException
import com.delarax.icewindDale.companion.models.harpos.HarposHoliday.*

fun HarposDate.isLeapYear() : Boolean = this.year.isLeapYear()

fun HarposDate.isValid() : Boolean {
    return !(
        (this.month == null && this.holiday == null) ||
        (this.month != null && this.holiday != null) ||
        (this.year < 1) ||
        (this.day !in (1..30)) ||
        (this.holiday != null && this.day != 1) ||
        (this.holiday == MIDWINTER && !this.isLeapYear())
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

    return this.month?.lastHoliday() ?: this.holiday!!
}

/**
 * The next holiday that will occur, not including today if it's a holiday
 */
@Throws(InvalidDateException::class)
fun HarposDate.nextHoliday() : HarposHoliday {
    if (!this.isValid()) { throw InvalidDateException(this) }

    return this.month?.nextHoliday(this.year) ?: this.holiday!!.let { currentHoliday ->
        if (currentHoliday == MIDWINTER) {
            SHIELDMEET // Have to hard code this one because there's no in-between month
        } else {
            currentHoliday.nextMonth.let { nextMonth ->
                if (nextMonth == HarposMonth.HAMMER) {
                    nextMonth.nextHoliday(this.year + 1)
                } else {
                    nextMonth.nextHoliday(this.year)
                }
            }
        }
    }
}

/**
 * The number of holidays that have occurred so far, including the current day if it's a holiday.
 */
@Throws(InvalidDateException::class)
fun HarposDate.numHolidaysPassed() : Int {
    if (!this.isValid()) { throw InvalidDateException(this) }

    val lastHoliday = this.month?.lastHoliday() ?: this.holiday!!
    val numYearlyHolidaysPassed =
        if (lastHoliday == MOON_FEAST && this.month == HarposMonth.HAMMER) {
            0
        } else {
            lastHoliday.ordinal
        }

    return if (this.isLeapYear() && this.month != HarposMonth.HAMMER) {
        numYearlyHolidaysPassed + 1
    } else {
        numYearlyHolidaysPassed
    }
}

@Throws(InvalidDateException::class)
fun HarposDate.absoluteDayNumber(): Int {
    if (!this.isValid()) { throw InvalidDateException(this) }

    // every month has 30 days in the Harpos calendar
    return this.month?.let { month ->
        month.ordinal * 30 + this.day + this.numHolidaysPassed()
    } ?: this.holiday!!.priorMonth.num * 30 + this.numHolidaysPassed()
}
